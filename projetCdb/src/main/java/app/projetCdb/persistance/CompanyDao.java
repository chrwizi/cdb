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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.projetCdb.models.Company;

@Repository
public class CompanyDao {
	// access to database
	private IDbAccess dbAccess=DbAccess.getInstance();
	
	
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
	//
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public CompanyDao(IDbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	public CompanyDao() {
		this.dbAccess = DbAccess.getInstance();
	}

	/**
	 * 
	 * @param compagny company to add in companies table
	 * @return
	 * @throws SQLException if connection database failure
	 */
	public OptionalLong Add(Company company) {
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

	/**
	 * 
	 * @param company update compagny int table
	 * @throws SQLException if connection to database faillure
	 */
	public void update(Company company) throws SQLException {
		Connection connection = dbAccess.getConnection();
		PreparedStatement updatePStatement = connection.prepareStatement(UPDATE_QUERY);
		updatePStatement.setString(1, company.getName());
		updatePStatement.setLong(2, company.getId());
		updatePStatement.executeUpdate();
		connection.close();
	}

	public Optional<Company> findById(Long id) {
		Optional<Company> optional = Optional.empty();
		if (id == null) {
			return optional;
		}
		Connection connection = null;
		try {
			connection = dbAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
			preparedStatement.setLong(1, id);
			// execute statement
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				optional = Optional.of(new Company(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return optional;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public  boolean isIdPresent(Long id, IDbAccess access) throws SQLException {
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
		return (results!=null)?(results.first() ? true : false):false;
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
		ArrayList<Company> listOfCompanies = new ArrayList<Company>();
		try (Connection connection = dbAccess.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				listOfCompanies.add(new Company(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2)));
			}
		} catch (SQLException e) {
			logger.debug("erreur dans le findAll");
		}
		return listOfCompanies;
	}

}
