package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;

public class ComputerDao {
	private IDbAccess access=DbAccess.getInstance();
	/*Name table*/
	private final static String TABLE="computer";
	/*fields table*/
	private final static String FIELD_1="id";
	private final static String FIELD_2="name";
	private final static String FIELD_3="introduced";
	private final static String FIELD_4="discontinued";
	private final static String FIELD_5="company_id";
	
	
	
	public ComputerDao(IDbAccess access) {
		super();
		this.access = access;
	}

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
	
	public void delete(Long id) {
		//TODO
	}
	
	public List<Computer> findAll() throws SQLException{
		String query="SELECT * FROM "+TABLE;
		ArrayList<Computer> computers=new ArrayList<Computer>();
		Connection connection=access.getConnection();
		Statement statement=connection.createStatement();
		ResultSet resultSet=statement.executeQuery(query);
		while(resultSet.next()) {
			computers.add(
							new Computer(
										resultSet.getLong(FIELD_1),
										resultSet.getString(FIELD_2),
										resultSet.getString(FIELD_3),
										resultSet.getString(FIELD_4),
										resultSet.getLong(FIELD_5)));
		}
		return computers;
	}
	
	
	
}
