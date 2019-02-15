package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Computer;

public class ComputerDao {
	private IDbAccess access = DbAccess.getInstance();
	/* Name table */
	private final static String TABLE = "computer";
	/* fields table */
	private final static String FIELD_1 = "id";
	private final static String FIELD_2 = "name";
	private final static String FIELD_3 = "introduced";
	private final static String FIELD_4 = "discontinued";
	private final static String FIELD_5 = "company_id";

	public ComputerDao(IDbAccess access) {
		super();
		this.access = access;
	}

	/**
	 * Add computer given in parameter in computers table
	 * 
	 * @param computer: computer to add in computers table
	 * @throws SQLException               if connection with database failure
	 * @throws IDCompanyNotFoundException if the Id of company given in parameter
	 *                                    don't exit in Companies table
	 */
	public void add(Computer computer) throws SQLException, IDCompanyNotFoundException {
		Connection connection = access.getConnection();
		String query = "INSERT INTO " + TABLE + "(" + FIELD_2 + "," + FIELD_3 + "," + FIELD_4 + "," + FIELD_5 + ") "
				+ "VALUES ('" + computer.getName() + "'" + ",'" + computer.getIntroduced() + "'" + ",'"
				+ computer.getDiscontinued() + "'" + ",'" + computer.getCompany_id() + "')";

		Statement statement = connection.createStatement();
		System.out.println(query);
		statement.executeUpdate(query);
		connection.close();
		// }
	}

	/**
	 * Verify if id given in argument is present in computers table
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean isIdPresent(Long id) throws SQLException {
		Connection connection = access.getConnection();
		String query = "SELECT * FROM " + TABLE + "WHERE " + FIELD_1 + "=" + id;
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		return results.first() ? true : false;
	}

	public void update(Computer computer) throws SQLException {
		Connection connection = access.getConnection();
		String query = "UPDATE " + TABLE + " SET " + FIELD_2 + "='" + computer.getName() + "'" + "," + FIELD_3 + "='"
				+ computer.getIntroduced() + "'" + "," + FIELD_4 + "='" + computer.getDiscontinued() + "'" + ","
				+ FIELD_5 + "='" + computer.getCompany_id() + "'" + " WHERE " + FIELD_1 + "=" + computer.getId() + ")";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		connection.close();

	}

	/**
	 * delete computer witch id given in parameter from computers table
	 * 
	 * @param id : id of computer to delete
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id) throws SQLException {
		String query = "DELETE FROM " + TABLE + " WHERE " + FIELD_1 + " =" + id;
		Connection connection = access.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		connection.close();
	}

	/**
	 * 
	 * @return list of all computers in database
	 * @throws SQLException
	 */
	public List<Computer> findAll() throws SQLException {
		String query = "SELECT * FROM " + TABLE;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		Connection connection = access.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
					resultSet.getTimestamp(FIELD_3), resultSet.getTimestamp(FIELD_4), resultSet.getLong(FIELD_5)));
		}
		return computers;
	}

}
