package app.projetCdb.persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;

@Repository
public class ComputerDao {	
	//@Autowired
	private IDbAccess access=DbAccess.getInstance();
	//@Autowired
	private CompanyDao companyDao=new CompanyDao();
	
	/* Name table */
	private final static String TABLE = "computer";

	/* fields table */
	private final static String FIELD_1 = "id";
	private final static String FIELD_2 = "name";
	private final static String FIELD_3 = "introduced";
	private final static String FIELD_4 = "discontinued";
	private final static String FOREIGN_KEY_COMPANY_ID = "company_id";

	/* Queries */
	private final static String CREATE_QUERY = "INSERT INTO " + TABLE + "(" + FIELD_2 + "," + FIELD_3 + "," + FIELD_4
			+ "," + FOREIGN_KEY_COMPANY_ID + ") " + "VALUES (?,?,?,?)";
	private final static String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_1 + " =?";

	private final static String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + FIELD_2 + "=?, " + FIELD_3 + "=?, "
			+ FIELD_4 + "=?," + "," + FOREIGN_KEY_COMPANY_ID + "=?, " + " WHERE " + FIELD_1 + "=?)";

	private final static String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE " + FIELD_1 + " = ? ";
	private final static String GET_PAGE_QUERY = "SELECT * FROM " + TABLE + " LIMIT ?,? ";
	private final static String SEARCH_COMPUTER_QUERY = "SELECT * FROM " + TABLE + "  WHERE " + FIELD_2
			+ " LIKE ?  LIMIT ?,?";
	private final static String SEARCH_COUNT_QUERY = "SELECT COUNT(" + FIELD_1 + ") as count FROM " + TABLE + " WHERE "
			+ FIELD_2 + " LIKE ?";
	
	private final static String QUERY_SORT_BY_NAME_ASC = "SELECT * FROM " + TABLE+" ORDER BY "+FIELD_2+" ASC"; 
	private final static String QUERY_SORT_BY_NAME_DESC = "SELECT * FROM " + TABLE+" ORDER BY "+FIELD_2+" DESC"; ; 

	
	
	public ComputerDao() {
		this.access=DbAccess.getInstance();
	}

	public ComputerDao(IDbAccess access) {
		this.access = access;
	}

	public static String getTable() {
		return TABLE;
	}

	public static String getForignKeyCompanyId() {
		return FOREIGN_KEY_COMPANY_ID;
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
		preparedstatement.setObject(2, (computer.getIntroduced()));
		preparedstatement.setObject(3, computer.getDiscontinued());
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
	public Optional<Computer> findById(Long id) throws SQLException {
		Optional<Computer> optional = Optional.empty();
		if(id==null) {
			return optional;
		}
		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, id);
			// execute statement
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FOREIGN_KEY_COMPANY_ID));
				Company company = null;
				if (optionalCompany.isPresent()) {
					company = optionalCompany.get();
				}
				LocalDate introduced = (LocalDate) resultSet.getObject(FIELD_3);
				LocalDate discontinued = (LocalDate) resultSet.getObject(FIELD_4);

				LocalDate introDate = (introduced != null) ? introduced : null;
				LocalDate discont = (discontinued != null) ? discontinued : null;
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
			preparedStatement.setObject(2, computer.getIntroduced());
			preparedStatement.setObject(3, computer.getDiscontinued());
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
	public List<Computer> findAll() throws SQLException {
		String query = "SELECT * FROM " + TABLE;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = access.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FOREIGN_KEY_COMPANY_ID));
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						(resultSet.getTimestamp(FIELD_3) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_3).toLocalDateTime().toLocalDate()
								: null,
						(resultSet.getTimestamp(FIELD_4) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_4).toLocalDateTime().toLocalDate()
								: null,
						optionalCompany.isPresent() ? optionalCompany.get() : null));
			}
		} catch (SQLException e) {
			throw e;
		}
		return computers;
	}
	
	public List<Computer> sortByName(boolean asc) throws SQLException {
		String query =asc?QUERY_SORT_BY_NAME_ASC:QUERY_SORT_BY_NAME_DESC;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = access.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FOREIGN_KEY_COMPANY_ID));
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						(resultSet.getTimestamp(FIELD_3) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_3).toLocalDateTime().toLocalDate()
								: null,
						(resultSet.getTimestamp(FIELD_4) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_4).toLocalDateTime().toLocalDate()
								: null,
						optionalCompany.isPresent() ? optionalCompany.get() : null));
			}
		} catch (SQLException e) {
			throw e;
		}
		return computers;
	}
	
	public List<Computer> sortBycompany(boolean asc) throws SQLException {
		//TODO change request
		String query =asc?QUERY_SORT_BY_NAME_ASC:QUERY_SORT_BY_NAME_DESC;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = access.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FOREIGN_KEY_COMPANY_ID));
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						(resultSet.getTimestamp(FIELD_3) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_3).toLocalDateTime().toLocalDate()
								: null,
						(resultSet.getTimestamp(FIELD_4) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_4).toLocalDateTime().toLocalDate()
								: null,
						optionalCompany.isPresent() ? optionalCompany.get() : null));
			}
		} catch (SQLException e) {
			throw e;
		}
		return computers;
	}

	public List<Computer> findAll(int sizePage, int offset) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(GET_PAGE_QUERY);
			preparedStatement.setInt(1, sizePage);
			preparedStatement.setInt(2, offset);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FOREIGN_KEY_COMPANY_ID));
				Company company = null;
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						(resultSet.getTimestamp(FIELD_3) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_3).toLocalDateTime().toLocalDate()
								: null,
						(resultSet.getTimestamp(FIELD_4) != null)
								? (LocalDate) resultSet.getTimestamp(FIELD_4).toLocalDateTime().toLocalDate()
								: null,
						optionalCompany.isPresent() ? optionalCompany.get() : company));
			}
		} catch (SQLException e) {
			throw e;
		}
		
		return computers;
	}

	public List<Computer> search(int sizePage, int offset, String computerName) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COMPUTER_QUERY);
			preparedStatement.setString(1, "%" + computerName + "%");
			preparedStatement.setInt(2, sizePage);
			preparedStatement.setInt(3, offset);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FOREIGN_KEY_COMPANY_ID));
				Company company = null;
				computers.add(new Computer(resultSet.getLong(FIELD_1), resultSet.getString(FIELD_2),
						(LocalDate) resultSet.getObject(FIELD_3), (LocalDate) resultSet.getObject(FIELD_4),
						optionalCompany.isPresent() ? optionalCompany.get() : company));
			}
		} catch (SQLException e) {
			throw e;
		}
		return computers;
	}

	public int count() throws SQLException {
		String query = "SELECT COUNT(" + FIELD_1 + ") as count FROM " + TABLE;
		ResultSet resultSet;
		int counter = 0;
		try (Connection connection = access.getConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			counter = (resultSet.first() ? resultSet.getInt("count") : 0);
		} catch (SQLException e) {
			throw e;
		}
		return counter;
	}

	public int seachcount(String name) throws SQLException {
		ResultSet resultSet;
		int counter = 0;
		try (Connection connection = access.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COUNT_QUERY);
			preparedStatement.setString(1, "%" + name + "%");
			resultSet = preparedStatement.executeQuery();
			counter = (resultSet.first() ? resultSet.getInt("count") : 0);
		} catch (SQLException e) {
			throw e;
		}
		return counter;
	}

}
