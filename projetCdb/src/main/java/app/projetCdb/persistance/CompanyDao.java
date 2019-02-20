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

import app.projetCdb.models.Company;

public class CompanyDao {
	// access to database
	private IDbAccess dbAccess;
	/* Name of table */
	private final static String TABLE = "company";
	/* fields of table */
	private final static String FIELD_1 = "id";
	private final static String FIELD_2 = "name";

	/* queries */
	private final static String CREATE_QUERY = " INSERT INTO " + TABLE + "(" + FIELD_2 + ") VALUES(?)";
	private final static String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_2 + "=? WHERE " + FIELD_1 + "=?";
	private final static String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_1 + "=?";
	private final static String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE " + FIELD_1 + "=?";
	
	public CompanyDao(IDbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	/**
	 * 
	 * @param compagny company to add in companies table
	 * @return 
	 * @throws SQLException if connection database failure
	 */
	public OptionalLong Add(Company company){
		OptionalLong optionalId=OptionalLong.empty();
		Connection connection;
		PreparedStatement preparedStatement ;
		try {
			connection = dbAccess.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, company.getName());
			preparedStatement.executeUpdate();
			ResultSet keyResult=preparedStatement.getGeneratedKeys();
			if(keyResult.first())optionalId=OptionalLong.of(keyResult.getLong(1));
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
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
		preparedStatement.setString(1, company.getName());
		preparedStatement.setLong(2, company.getId());
		preparedStatement.executeUpdate();
		connection.close();
	}

	
	
	public Optional<Company> findById(Long id){
		Optional<Company> optional=Optional.empty();
		Connection connection;
		try {
			connection = dbAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setLong(1, id);
			// execute statement
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				//there is a result
				optional=Optional.of(new Company(resultSet.getLong(FIELD_1),resultSet.getString(FIELD_2)));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optional;
	}
	
	
	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static boolean isIdPresent(Long id, IDbAccess access) throws SQLException {
		access = DbAccess.getInstance();
		Connection connection = access.getConnection();
		String query = "SELECT * FROM " + TABLE + "WHERE " + FIELD_1 + "=" + id;
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		connection.close();
		return results.first() ? true : false;
	}


	/**
	 * 
	 * @param id / id of company to delete in table
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id){
		Connection connection;
		try {
			connection = dbAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Company> findAll() throws SQLException {
		String query = "SELECT * FROM " + TABLE;
		Connection connection = dbAccess.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		ArrayList<Company> listOfCompanies = new ArrayList<Company>();
		while (resultSet.next()) {
			listOfCompanies.add(new Company(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2)));
		}
		return listOfCompanies;
	}

}
