package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

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

	/* Queries */
	private final static String CREATE_QUERY = "INSERT INTO " + TABLE + "(" + FIELD_2 + "," + FIELD_3 + "," + FIELD_4
			+ "," + FIELD_5 + ") " + "VALUES (?,?,?,?)";
	private final static String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_1 + " =?";

	private final static String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_2 + "=?, " + FIELD_3 + "=?, "
			+ FIELD_4 + "=?," + "," + FIELD_5 + "=?, " + " WHERE " + FIELD_1 + "=?)";

	private final static String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE " + FIELD_1 + " = ? ";

	
	
	
	public ComputerDao(IDbAccess access) {
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
	public OptionalLong add(Computer computer) throws SQLException, IDCompanyNotFoundException {
		Connection connection = access.getConnection();
		// prepare statement
		PreparedStatement preparedstatement = connection.prepareStatement(CREATE_QUERY,
				Statement.RETURN_GENERATED_KEYS);
		// set statement parametters
		preparedstatement.setString(1, computer.getName());
		preparedstatement.setTimestamp(2,new Timestamp( computer.getIntroduced().getTime()));
		preparedstatement.setTimestamp(3,new Timestamp( computer.getDiscontinued().getTime()));
		preparedstatement.setLong(4, computer.getCompany_id());
		// execute statement
		preparedstatement.executeUpdate();
		ResultSet keyResult = preparedstatement.getGeneratedKeys();
		OptionalLong optionalId = OptionalLong.empty();
		if (keyResult.first())
			optionalId=OptionalLong.of(keyResult.getLong(1));
		connection.close();
		return optionalId;
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

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(long id) {
		Optional<Computer> optional = Optional.empty();
		Connection connection;
		try {
			connection = access.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, id);
			// execute statement
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				optional = Optional.of(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						new java.util.Date(resultSet.getTimestamp(FIELD_3).getTime()), 
						new java.util.Date(resultSet.getDate(FIELD_4).getTime()), resultSet.getLong(FIELD_5)));

			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optional;

	}

	public OptionalLong update(Computer computer) throws SQLException {
		Connection connection = access.getConnection();
		// prepare statement
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, computer.getName());
		preparedStatement.setDate(2, (Date) computer.getIntroduced());
		preparedStatement.setDate(3, (Date) computer.getDiscontinued());
		preparedStatement.setLong(4, computer.getId());
		// execute
		preparedStatement.executeUpdate();
		// get updated computer id
		ResultSet keyResult = preparedStatement.getGeneratedKeys();
		OptionalLong optionalId = OptionalLong.empty();
		if (keyResult.first())
			OptionalLong.of(keyResult.getLong(1));
		connection.close();
		return optionalId;
	}

	/**
	 * delete computer witch id given in parameter from computers table
	 * 
	 * @param id : id of computer to delete
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id) throws SQLException {
		Connection connection = access.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setLong(1, id);
		preparedStatement.executeUpdate();
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
					resultSet.getDate(FIELD_3), resultSet.getDate(FIELD_4), resultSet.getLong(FIELD_5)));
		}
		return computers;
	}

}
