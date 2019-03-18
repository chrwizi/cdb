package app.projetCdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import app.projetCdb.models.Company;

@Repository("companyDao")
public class CompanyDao {
	// access to database
	private IDbAccess dbAccess;

	/* Name of table */
	private final static String TABLE = "company";
	private final static String COMPUTER_TABLE = ComputerDao.getTable();
	private final static String COMPANY_ID_IN_COMPUTER_TABLE = ComputerDao.getForignKeyCompanyId();
	/* fields of table */
	private final static String FIELD_1 = "id";
	private final static String FIELD_2 = "name";
	/* queries */
	private final static String CREATE_QUERY = " INSERT INTO " + TABLE + "(" + FIELD_2 + ") VALUES(?)";
	private final static String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_2 + "=? WHERE " + FIELD_1 + "=?";
	private final static String DELETE_COMPANY_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_1 + "=?";
	private final static String DELETE_ASSOCIATED_COMPUTERS = "DELETE  FROM " + COMPUTER_TABLE + " WHERE "
			+ COMPANY_ID_IN_COMPUTER_TABLE + " =?";
	private final static String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE " + FIELD_1 + "=?";
//	private final static String QUERY_SORT_BY_NAME_ASC = "SELECT * FROM " + TABLE + " ORDER BY " + FIELD_2 + " ASC";
//	private final static String QUERY_SORT_BY_NAME_DESC = "SELECT * FROM " + TABLE + " ORDER BY " + FIELD_2 + " DESC";
//	//
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public CompanyDao(IDbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	// company mapper from database
	private RowMapper<Company> companyMapper = (resultSet, rowNum) -> new Company(resultSet.getLong(FIELD_1),
			resultSet.getString(FIELD_2));

	/**
	 * 
	 * @param compagny company to add in companies table
	 * @return
	 * @throws SQLException if connection database failure
	 */
	public OptionalLong AddTmp(Company company) {
		OptionalLong optionalId = OptionalLong.empty();
		Connection connection;
		PreparedStatement addPStatement;
		try {
			connection = dbAccess.getConnection();
			addPStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			addPStatement.setString(1, company.getName());
			addPStatement.executeUpdate();
			ResultSet keyResult = addPStatement.getGeneratedKeys();
			if (keyResult.first())
				optionalId = OptionalLong.of(keyResult.getLong(1));
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optionalId;
	}

	public OptionalLong Add(Company company) {
		OptionalLong optionalId = OptionalLong.empty();

		KeyHolder keyholder = new GeneratedKeyHolder();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dbAccess.getDatasource());
		try {
			jdbcTemplate.update(connection -> {
				PreparedStatement addPStatement = connection.prepareStatement(CREATE_QUERY,
						Statement.RETURN_GENERATED_KEYS);
				addPStatement.setString(1, company.getName());
				return addPStatement;
			}, keyholder);
		} catch (DataAccessException e) {
			logger.debug("erreur jdbc template");
		}
		optionalId = OptionalLong.of((long) keyholder.getKey());
		return optionalId;
	}

	/**
	 * 
	 * @param company update compagny int table
	 * @throws SQLException if connection to database faillure
	 */
	public void update(Company company) throws SQLException {
		JdbcTemplate template = new JdbcTemplate(dbAccess.getDatasource());

		try {
			template.update(UPDATE_QUERY, company.getName(), company.getId());
		} catch (DataAccessException e) {
			logger.debug("erreur update company ");
		}
	}

	public Optional<Company> findById(Long id) {
		Optional<Company> optional = Optional.empty();
		if (id == null) {
			return optional;
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dbAccess.getDatasource());
		
		try {
			optional = Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, this.companyMapper, id));
		} catch (DataAccessException e) {
			logger.debug("erreur find company by Id");
		}
		return optional;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean isIdPresent(Long id, IDbAccess access) throws SQLException {
		access = DbAccess.getInstance();
		ResultSet results = null;
		try (Connection connection = access.getConnection()) {
			String query = "SELECT * FROM " + TABLE + "WHERE " + FIELD_1 + "=" + id;
			Statement statement = connection.createStatement();
			results = statement.executeQuery(query);
			connection.close();
		} catch (Exception e) {
			logger.debug("erreur sql dans le ispresent");
		}
		return (results != null) ? (results.first() ? true : false) : false;
	}

	/**
	 * 
	 * @param id / id of company to delete in table and associated computers
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id) {
		try (Connection connection = dbAccess.getConnection()) {
			// prepare statements
			PreparedStatement deleteCompanyPStatement = connection.prepareStatement(DELETE_COMPANY_QUERY);
			PreparedStatement deleteComputersPStatement = connection.prepareStatement(DELETE_ASSOCIATED_COMPUTERS);
			deleteCompanyPStatement.setLong(1, id);
			deleteComputersPStatement.setLong(1, id);
			// begin transaction
			connection.setAutoCommit(false);
			try {
				logger.debug("début de la transaction de suppression");
				deleteComputersPStatement.executeUpdate();
				logger.debug("entre 2 stmnt");
				deleteCompanyPStatement.executeUpdate();
				connection.commit();
				logger.debug("suppression d'une companie effectuée");
			} catch (SQLException e) {
				connection.rollback();
				logger.debug("transaction de supression de companie annulée");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			logger.debug("échec de connexion à la base de données ");
		}
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Company> findAll() throws SQLException {
		String query = "SELECT * FROM " + TABLE;
		ArrayList<Company> companies = new ArrayList<Company>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dbAccess.getDatasource());
		try {
				companies=(ArrayList<Company>) jdbcTemplate.query(query, this.companyMapper);
		}
		catch (DataAccessException e) {
			logger.debug("erreur find All companies");
		}
		return companies;
	}

}
