package app.projetCdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import javax.persistence.EntityManager;

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
	private JdbcTemplate jdbcTemplate;
	private EntityManager entityManager;

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

	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public CompanyDao(IDbAccess dbAccess) {
		this.dbAccess = dbAccess;
		jdbcTemplate = new JdbcTemplate(dbAccess.getDatasource());
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
	public OptionalLong Add(Company company) {
		OptionalLong optionalId = OptionalLong.empty();

		try {
			KeyHolder keyholder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement addPStatement = connection.prepareStatement(CREATE_QUERY,
						Statement.RETURN_GENERATED_KEYS);
				addPStatement.setString(1, company.getName());
				return addPStatement;
			}, keyholder);

			optionalId = OptionalLong.of((long) keyholder.getKey());
		} catch (DataAccessException e) {
			logger.debug("erreur jdbc template");
		}

		return optionalId;
	}

	/**
	 * 
	 * @param company update compagny int table
	 * @throws SQLException if connection to database faillure
	 */
	public void update(Company company) throws SQLException {
		try {
			jdbcTemplate.update(UPDATE_QUERY, company.getName(), company.getId());
		} catch (DataAccessException e) {
			logger.debug("erreur update company ");
		}
	}

	public Optional<Company> findById(Long id) {
		Optional<Company> optional = Optional.empty();

		if (id == null) {
			return optional;
		}

		try {
			optional = Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, this.companyMapper, id));
		} catch (DataAccessException e) {
			logger.debug("erreur find company by Id");
		}

		return optional;
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

		try {
			companies = (ArrayList<Company>) jdbcTemplate.query(query, this.companyMapper);
		} catch (DataAccessException e) {
			logger.debug("erreur find All companies");
		}
		return companies;
	}

}
