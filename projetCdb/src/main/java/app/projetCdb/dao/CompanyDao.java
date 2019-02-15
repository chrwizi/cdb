package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.projetCdb.models.Company;

public class CompanyDao {
	// access to database
	private IDbAccess dbAccess;
	/* Name of table */
	private final static String TABLE = "company";
	/* fields of table */
	private final static String FIELD_1 = "id";
	private final static String FIELD_2 = "name";

	public CompanyDao(IDbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	/**
	 * 
	 * @param compagny company to add in companies table
	 * @throws SQLException if connection database failure
	 */
	public void Add(Company company) throws SQLException {
		String query = " INSERT INTO " + TABLE + "(" + FIELD_2 + ") VALUES('" + company.getName() + "')";
		Connection connection = dbAccess.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		connection.close();
	}

	/**
	 * 
	 * @param company update compagny int table
	 * @throws SQLException if connection to database faillure
	 */
	public void update(Company company) throws SQLException {
		String query = "UPDATE " + TABLE + " SET " + FIELD_2 + "='" + company.getName() + "' WHERE " + FIELD_1 + "="
				+ company.getId();
		Connection connection = dbAccess.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		connection.close();
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
		return results.first() ? true : false;
	}

	/**
	 * 
	 * @param id / id of company to delete in table
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id) throws SQLException {
		String query = "DELETE FROM " + TABLE + " WHERE " + FIELD_1 + " =" + id;
		Connection connection = dbAccess.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		connection.close();
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
