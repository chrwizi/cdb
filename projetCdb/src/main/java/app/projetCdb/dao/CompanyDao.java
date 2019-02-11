package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import app.projetCdb.models.Company;

public class CompanyDao {
	private DbAccess dbAccess=DbAccess.getInstance();
	/*Name of table*/
	private final static String TABLE="company";
	/*fields of table*/
	private final static String FIELD_1="id";
	private final static String FIELD_2="name";
	
	/**
	 * 
	 * @param compagny compagny  to add in companies table
	 * @throws SQLException if connection database faillure
	 */
	public void Add(Company company) throws SQLException {
		String query=" INSERT INTO "+TABLE+"("+FIELD_2+") VALUES('"+company.getName()+"')";
		Connection connection=dbAccess.getConnection();
		Statement statement=connection.createStatement();
		int resultSet=statement.executeUpdate(query);
		connection.close();
	}
	
	/**
	 * 
	 * @param company update compagny int table
	 * @throws SQLException if connection to database faillure
	 */
	public void update(Company company) throws SQLException {
		String query="UPDATE "+TABLE+" SET "+FIELD_2+"='"+company.getName()+"' WHERE "+FIELD_1+"="+company.getId();
		Connection connection=dbAccess.getConnection();
		Statement statement=connection.createStatement();
		int resultSet=statement.executeUpdate(query);
		connection.close();
	}
	
	/**
	 * 
	 * @param id / id of company to delete in table
	 * @throws SQLException if connection to database faillure
	 */
	public void delete(Long id) throws SQLException {
		String query="DELETE FROM "+TABLE+" WHERE "+FIELD_1+" ="+id;
		Connection connection=dbAccess.getConnection();
		Statement statement=connection.createStatement();
		int resultSet=statement.executeUpdate(query);
		connection.close();
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Set<Company> findAll() throws SQLException{
		String query="SELECT * FROM "+TABLE;
		Connection connection=dbAccess.getConnection();
		Statement statement=connection.createStatement();
		ResultSet result=statement.executeQuery(query);
		return null;
	}
	
	
}
