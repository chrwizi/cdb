package app.projetCdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;

@Repository("computerDao")
public class ComputerDao {

	// private DataSource datasource;
	private IDbAccess datasource;
	JdbcTemplate jdbcTemplate;

	private CompanyDao companyDao;

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
			+ FIELD_4 + "=?, " + FOREIGN_KEY_COMPANY_ID + "=? " + " WHERE " + FIELD_1 + "=?";

	private final static String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE " + FIELD_1 + " = ? ";
	private final static String GET_PAGE_QUERY = "SELECT * FROM " + TABLE + " LIMIT ?,? ";
	private final static String SEARCH_COMPUTER_QUERY = "SELECT * FROM " + TABLE + "  WHERE " + FIELD_2
			+ " LIKE ?  LIMIT ?,?";
	private final static String SEARCH_COUNT_QUERY = "SELECT COUNT(" + FIELD_1 + ") as count FROM " + TABLE + " WHERE "
			+ FIELD_2 + " LIKE ?";

	private final static String QUERY_SORT_BY_NAME_ASC = "SELECT * FROM " + TABLE + " ORDER BY " + FIELD_2
			+ " ASC LIMIT ?,? ";
	private final static String QUERY_SORT_BY_NAME_DESC = "SELECT * FROM " + TABLE + " ORDER BY " + FIELD_2
			+ " DESC LIMIT ?,? ";

	private Logger logger = LoggerFactory.getLogger(ComputerDao.class);

	public ComputerDao(IDbAccess datasource,CompanyDao companyDao) {
		this.datasource = datasource;
		this.companyDao=companyDao;
		jdbcTemplate = new JdbcTemplate(datasource.getDatasource());
	}

	public static String getTable() {
		return TABLE;
	}

	public static String getForignKeyCompanyId() {
		return FOREIGN_KEY_COMPANY_ID;
	}

	private RowMapper<Computer> computerMaper = new RowMapper<Computer>() {
		@Override
		public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Optional<Company> optionalCompany = companyDao.findById(rs.getLong(FOREIGN_KEY_COMPANY_ID));
			Computer computer = new Computer(rs.getLong(FIELD_1), rs.getString(FIELD_2),
					(rs.getTimestamp(FIELD_3) != null)
							? (LocalDate) rs.getTimestamp(FIELD_3).toLocalDateTime().toLocalDate()
							: null,
					(rs.getTimestamp(FIELD_4) != null)
							? (LocalDate) rs.getTimestamp(FIELD_4).toLocalDateTime().toLocalDate()
							: null,
					optionalCompany.isPresent() ? optionalCompany.get() : null);
			return computer;
		}
	};

	/**
	 * Add computer given in parameter in computers table
	 * 
	 * @param computer: computer to add in computers table
	 * @throws SQLException               if connection with database failure
	 * @throws IDCompanyNotFoundException if the Id of company given in parameter
	 *                                    don't exit in Companies table
	 */
	public OptionalLong add(Computer computer) throws SQLException, IDCompanyNotFoundException {
		OptionalLong optionalId = OptionalLong.empty();
		if (computer == null) {
			return null;
		}
		KeyHolder keyholder = new GeneratedKeyHolder();

		try {
			jdbcTemplate.update(connection -> {
				PreparedStatement preparedstatement = connection.prepareStatement(CREATE_QUERY,
						Statement.RETURN_GENERATED_KEYS);
				// set statement parameters
				preparedstatement.setString(1, computer.getName());
				preparedstatement.setObject(2, (computer.getIntroduced()));
				preparedstatement.setObject(3, computer.getDiscontinued());
				preparedstatement.setLong(4, computer.getCompany().getId());
				return preparedstatement;

			}, keyholder);
			optionalId = OptionalLong.of((keyholder.getKey().longValue()));
		} catch (DataAccessException e) {
			logger.debug("erreur sur add computer : " + e.getMessage());
		}

		return optionalId;
	}

	/**
	 * Verify if id given in argument is present in computers table
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean isIdPresent(Long id) {
		boolean find = false;

		try (Connection connection = datasource.getConnection()) {
			String query = "SELECT * FROM " + TABLE + "WHERE " + FIELD_1 + "=" + id;
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			find = results.first();
		} catch (SQLException e) {
			logger.debug("Erreur isPresent");
		}

		return find;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Optional<Computer> findById(Long id) throws SQLException {
		Optional<Computer> optional = Optional.empty();
		if (id == null) {
			return optional;
		}
		try {
			optional = Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, computerMaper, id));
		} catch (DataAccessException e) {
			logger.debug("erreur sur find computer by Id");
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
		if (computer == null) {
			return optionalId;
		}
		try {
			jdbcTemplate.update(UPDATE_QUERY, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(),
					computer.getCompany() != null ? computer.getCompany().getId() : 0, computer.getId());
			optionalId = OptionalLong.of(computer.getId());
		} catch (DataAccessException e) {
			logger.debug("erreur update computeur");
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
		try (Connection connection = datasource.getConnection()) {
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
		
		try {
			computers=(ArrayList<Computer>) jdbcTemplate.query(query,this.computerMaper);
		} catch (DataAccessException e) {
			logger.debug("erreur sur find all computers : "+e.getMessage());
		}
		
		return computers;
	}

	public List<Computer> sortBycompany(boolean asc) throws SQLException {
		// TODO change request
		String query = asc ? QUERY_SORT_BY_NAME_ASC : QUERY_SORT_BY_NAME_DESC;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		
		try {
				
			//computers=jdbcTemplate.query()
		} catch (DataAccessException e) {
			logger.debug("erreur sur sort company : "+e.getMessage());
		}
		
		try (Connection connection = datasource.getConnection()) {
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

		try (Connection connection = datasource.getConnection()) {
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

	public List<Computer> sortByName(boolean asc, int sizePage, int offset) throws SQLException {
		logger.debug("in Sort by name");
		String query = asc ? QUERY_SORT_BY_NAME_ASC : QUERY_SORT_BY_NAME_DESC;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = datasource.getConnection()) {

			PreparedStatement sortByNamePStatment = connection.prepareStatement(query);
			sortByNamePStatment.setInt(1, sizePage);
			sortByNamePStatment.setInt(2, offset);
			ResultSet resultSet = sortByNamePStatment.executeQuery();
			System.out.println("sortbyName asc: " + asc);
			while (resultSet.next()) {

				Optional<Company> optionalCompany = companyDao.findById(resultSet.getLong(FOREIGN_KEY_COMPANY_ID));
				Company company = null;
				logger.debug("sorted " + resultSet.getString(FIELD_2));
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
		try (Connection connection = datasource.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COMPUTER_QUERY);
			preparedStatement.setString(1, "%" + computerName + "%");
			preparedStatement.setInt(2, sizePage);
			preparedStatement.setInt(3, offset);
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

	public int count() throws SQLException {
		String query = "SELECT COUNT(" + FIELD_1 + ") as count FROM " + TABLE;
		ResultSet resultSet;
		int counter = 0;
		try (Connection connection = datasource.getConnection()) {
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
		try (Connection connection = datasource.getConnection()) {
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
