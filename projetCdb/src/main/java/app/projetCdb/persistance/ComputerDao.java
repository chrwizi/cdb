package app.projetCdb.persistance;

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
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;

public class ComputerDao {
	private IDbAccess access = DbAccess.getInstance();
	private CompanyDao companyDao;
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
	private final static String GET_PAGE_QUERY = "SELECT * FROM " + TABLE + " LIMIT ?,? ";
	private final static String SEARCH_COMPUTER_QUERY = "SELECT * FROM " + TABLE + "  WHERE " + FIELD_2
			+ " LIKE ?  LIMIT ?,?";
	private final static String SEARCH_COUNT_QUERY = "SELECT COUNT(" + FIELD_1 + ") as count FROM " + TABLE + " WHERE "
			+ FIELD_2 + " LIKE ?";

	public ComputerDao(IDbAccess access) {
		this.access = access;
		companyDao = new CompanyDao(access);
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
		// set statement parameters
		preparedstatement.setString(1, computer.getName());
		preparedstatement.setTimestamp(2, new Timestamp(computer.getIntroduced().getTime()));
		preparedstatement.setTimestamp(3, new Timestamp(computer.getDiscontinued().getTime()));
		preparedstatement.setLong(4, computer.getCompany().getId());
		// execute statement
		preparedstatement.executeUpdate();
		ResultSet keyResult = preparedstatement.getGeneratedKeys();
		OptionalLong optionalId = OptionalLong.empty();
		if (keyResult.first())
			optionalId = OptionalLong.of(keyResult.getLong(1));
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
	 * @throws SQLException 
	 */
	public Optional<Computer> findById(long id) throws SQLException {
		Optional<Computer> optional = Optional.empty();
		try (Connection  connection = access.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, id);
			// execute statement
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FIELD_5));
				Company company = null;
				if (optionalCompany.isPresent()) {
					company = optionalCompany.get();
				}
				Timestamp introduced = resultSet.getTimestamp(FIELD_3);
				Timestamp discontinued = resultSet.getTimestamp(FIELD_4);
				java.util.Date introDate = (introduced != null) ? new java.util.Date(introduced.getTime()) : null;
				java.util.Date discont = (discontinued != null) ? new java.util.Date(discontinued.getTime()) : null;
				optional = Optional.of(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2), introDate,
						discont, company));
			}
			connection.close();
		} catch (SQLException e) {
			throw e;
		}
		return optional;
	}

	/**
	 * 
	 * @param computer
	 * @return
	 * @throws SQLException
	 */
	public OptionalLong update(Computer computer) throws SQLException {
		OptionalLong optionalId = OptionalLong.empty();
		try (Connection connection = access.getConnection()) {
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
			if (keyResult.first())
				OptionalLong.of(keyResult.getLong(1));
		} catch (SQLException e) {
			throw e;
		}
		return optionalId;
	}

	/**
	 * delete computer witch id given in parameter from computers table
	 * 
	 * @param id : id of computer to delete
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id) throws SQLException {
		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * 
	 * @return list of all computers in database
	 * @throws SQLException
	 */
	public Optional<List<Computer>> findAll() throws SQLException {
		String query = "SELECT * FROM " + TABLE;
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connection = access.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FIELD_5));
				Company company = null;
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						resultSet.getDate(FIELD_3), resultSet.getDate(FIELD_4),
						optionalCompany.isPresent() ? optionalCompany.get() : company));
			}
		} catch (SQLException e) {
			throw e;
		}
		return (computers.isEmpty()) ? Optional.empty() : Optional.of((computers));
	}

	public Optional<List<Computer>> findAll(int sizePage, int offset) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(GET_PAGE_QUERY);
			preparedStatement.setInt(1, sizePage);
			preparedStatement.setInt(2, offset);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FIELD_5));
				Company company = null;
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						resultSet.getDate(FIELD_3), resultSet.getDate(FIELD_4),
						optionalCompany.isPresent() ? optionalCompany.get() : company));
			}
		} catch (SQLException e) {
			throw e;
		}
		return (computers.isEmpty()) ? Optional.empty() : Optional.of((computers));
	}

	public Optional<List<Computer>> search(int sizePage, int offset, String computerName) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COMPUTER_QUERY);
			preparedStatement.setString(1, "%" + computerName + "%");
			preparedStatement.setInt(2, sizePage);
			preparedStatement.setInt(3, offset);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FIELD_5));
				Company company = null;
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						resultSet.getDate(FIELD_3), resultSet.getDate(FIELD_4),
						optionalCompany.isPresent() ? optionalCompany.get() : company));
			}
		} catch (SQLException e) {
			throw e;
		}
		return (computers.isEmpty()) ? Optional.empty() : Optional.of((computers));
	}

	public int count() throws SQLException {
		String query = "SELECT COUNT(" + FIELD_1 + ") as count FROM " + TABLE;
		ResultSet resultSet;
		int counter=0;
		try (Connection connection = access.getConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			counter=(resultSet.first() ? resultSet.getInt("count") : 0);
		} catch (SQLException e) {
			throw e;
		}
		return counter;
	}

	public int seachcount(String name) throws SQLException {
		ResultSet resultSet;
		int counter=0;
		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COUNT_QUERY);
			preparedStatement.setString(1, "%" + name + "%");
			resultSet = preparedStatement.executeQuery();
			counter=(resultSet.first() ? resultSet.getInt("count") : 0);
		} catch (SQLException e) {
			throw e;
		}
		return counter;
	}

}
