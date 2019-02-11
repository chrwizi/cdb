package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import app.projetCdb.models.Computer;

public class ComputerDao {
	private DbAccess access=DbAccess.getInstance();
	/*Name table*/
	private final static String TABLE="computer";
	/*fields table*/
	private final static String FIELD_1="id";
	private final static String FIELD_2="name";
	private final static String FIELD_3="introduced";
	private final static String FIELD_4="discontinued";
	private final static String FIELD_5="company_id";
	
	/**
	 * 
	 * @param computer: computer to add in computers table
	 * @throws SQLException if connection with database faillure
	 */
	public void add(Computer computer) throws SQLException {
		Connection connection=access.getConnection();
		String query="INSERT INTO "+TABLE+"("+
					 FIELD_2+","+FIELD_3+","+
					 FIELD_4+","+FIELD_5+") "
		+ "VALUES ('"+computer.getName()+"','"+computer.getIntroduced()+"','"+
					 computer.getDiscontinued()+"','"+computer.getCompany_id()+"'";
		
		Statement statement=connection.createStatement();
		int resultSet=statement.executeUpdate(query);
		connection.close();
	}
	
	public void update(Computer computer) {
		//TODO
	}
	
	
}
