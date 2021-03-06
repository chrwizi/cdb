package cdb.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdb.core.models.Company;
import cdb.core.models.Computer;

@Repository("computerDao")
public class ComputerDao {

	// database access
	private DataSource datasource;
	private JdbcTemplate jdbcTemplate;
	private LocalSessionFactoryBean sessionFactory;
	private CriteriaBuilder criteriaBuilder;
	private Root<Computer> root;

	private CompanyDao companyDao;

	/* Name table */
	private final static String TABLE = "computer";
	/* table fields */
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
	private final String COUNT_QUERY = "SELECT COUNT(" + FIELD_1 + ") as count FROM " + TABLE;
	//

	private Logger logger = LoggerFactory.getLogger(ComputerDao.class);

	public ComputerDao(DataSource datasource, LocalSessionFactoryBean sessionFactory, CompanyDao companyDao) {
		this.sessionFactory = sessionFactory;
		this.companyDao = companyDao;
		this.datasource = datasource;
		jdbcTemplate = new JdbcTemplate(datasource);
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
	@Transactional
	public OptionalLong add(Computer computer) throws SQLException {
		logger.debug("\n\n>> in add Computer <<<\n\n");
		OptionalLong optionalId = OptionalLong.empty();
		if (computer != null) {

			try (Session session = sessionFactory.getObject().openSession()) {
				logger.debug("\n\n>> session ouverte <<<\n\n");
				Transaction transaction = session.beginTransaction();
				logger.debug("\n\n >>>> before persist <<<\n\n");
				session.persist(computer);
				logger.debug("\n\n>> before commit <<<\n\n");
				transaction.commit();
				logger.debug("\n\n>> aftre commit  <<<\n\n");
				
				optionalId = OptionalLong.of(computer.getId());
			} catch (HibernateException e) {
				logger.debug("Erreur sur add computer : " + e.getMessage());
			}
		}

		return optionalId;
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
		try (Session session = sessionFactory.getObject().openSession()) {

			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Computer> findCriteria = criteriaBuilder.createQuery(Computer.class);
			Root<Computer> root = findCriteria.from(Computer.class);
			findCriteria.select(root).where(criteriaBuilder.equal(root.get(FIELD_1), id));
			optional = session.createQuery(findCriteria).uniqueResultOptional();
		} catch (HibernateException e) {
			logger.debug("erreur sur find computer by Id: " + e.getMessage());
		}

		return optional;
	}

	/**
	 * 
	 * @param computer
	 * @return
	 * @throws SQLException
	 */
	public void update(Computer computer) throws SQLException {
		if (computer != null) {
			try (Session session = sessionFactory.getObject().openSession()) {

				criteriaBuilder = session.getCriteriaBuilder();
				CriteriaUpdate<Computer> updateCriteria = criteriaBuilder.createCriteriaUpdate(Computer.class);
				Root<Computer> root = updateCriteria.from(Computer.class);
				updateCriteria.set(FIELD_2, computer.getName());
				updateCriteria.set(FIELD_3, computer.getIntroduced());
				updateCriteria.set(FIELD_4, computer.getDiscontinued());
				updateCriteria.set("company", computer.getCompany());
				updateCriteria.where(criteriaBuilder.equal(root.get(FIELD_1), computer.getId()));
				Transaction transaction = session.beginTransaction();
				session.createQuery(updateCriteria).executeUpdate();
				transaction.commit();

			} catch (HibernateException e) {
				logger.debug("Erreur sur update computeur : " + e.getMessage());
			}
		}
	}

	/**
	 * delete computer witch id given in parameter from computers table
	 * 
	 * @param id : id of computer to delete
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id) throws SQLException {
		if (id != null) {
			try (Session session = sessionFactory.getObject().openSession()) {
				CriteriaDelete<Computer> criteriaDelete = criteriaBuilder.createCriteriaDelete(Computer.class);
				root = criteriaDelete.from(Computer.class);
				criteriaDelete.where(criteriaBuilder.equal(root.get(FIELD_1), id));

				Transaction transaction = session.beginTransaction();
				session.createQuery(criteriaDelete).executeUpdate();
				transaction.commit();
			} catch (HibernateException e) {
				logger.debug("Erreur sur delete computeur : " + e.getMessage());
			}

		}
	}

	/**
	 * 
	 * @return list of all computers in database
	 * @throws SQLException
	 */
	public List<Computer> findAll() throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();

		try (Session session = sessionFactory.getObject().openSession()) {
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Computer> findAllCriteria = criteriaBuilder.createQuery(Computer.class);
			root = findAllCriteria.from(Computer.class);
			findAllCriteria.select(root);
			computers = session.createQuery(findAllCriteria).getResultList();
		} catch (HibernateException e) {
			logger.debug("erreur sur find all computers : " + e.getMessage());
		}

		return computers;
	}

	public List<Computer> sortBycompany(boolean asc) throws SQLException {
		String query = asc ? QUERY_SORT_BY_NAME_ASC : QUERY_SORT_BY_NAME_DESC;
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try {
			computers = (ArrayList<Computer>) jdbcTemplate.query(query, this.computerMaper);
		} catch (DataAccessException e) {
			logger.debug("erreur sur sort company : " + e.getMessage());
		}

		return computers;
	}

	public List<Computer> findAll(int sizePage, int offset) throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();

		try (Session session = sessionFactory.getObject().openSession()) {
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Computer> findAllCriteria = criteriaBuilder.createQuery(Computer.class);
			root = findAllCriteria.from(Computer.class);
			findAllCriteria.select(root);
			computers = session.createQuery(findAllCriteria).setFirstResult(offset).setMaxResults(sizePage)
					.getResultList();
		} catch (HibernateException e) {
			logger.debug("Erreur sur find All computers : " + e.getMessage());
		}
		return computers;
	}

	public List<Computer> sortByName(boolean asc, int sizePage, int offset) throws SQLException {
		String query = asc ? QUERY_SORT_BY_NAME_ASC : QUERY_SORT_BY_NAME_DESC;
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try {
			computers = (ArrayList<Computer>) jdbcTemplate.query(query, computerMaper, sizePage, offset);
		} catch (DataAccessException e) {
			logger.debug("Erreu sur Sort By name :" + e.getMessage());
		}

		return computers;
	}

	public List<Computer> search(int sizePage, int offset, String computerName) throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();

		try (Session session = sessionFactory.getObject().openSession()) {

			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Computer> searchCriteria = criteriaBuilder.createQuery(Computer.class);
			root = searchCriteria.from(Computer.class);
			searchCriteria.select(root)
					.where(criteriaBuilder.like(root.get(FIELD_2), "%".concat(computerName).concat("%")));
			computers = session.createQuery(searchCriteria).setFirstResult(offset).setMaxResults(sizePage)
					.getResultList();
		} catch (HibernateException e) {
			logger.debug("Erreur sur search computer : " + e.getMessage());
		}

		return computers;
	}

	public int count() throws SQLException {
		Long counter = 0L;
		try (Session session = sessionFactory.getObject().openSession()) {
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
			root = countCriteria.from(Computer.class);
			countCriteria.select(criteriaBuilder.count(root));
			Query<Long> query = session.createQuery(countCriteria);
			counter = query.uniqueResult();
		} catch (HibernateException e) {
			logger.debug("Erreur dans count computers : " + e.getMessage());
		}
		return Integer.valueOf(counter.toString());
	}

	public int seachcount(String computerName) throws SQLException {
		Integer counter = 0;

		try {
			counter = jdbcTemplate.queryForObject(SEARCH_COUNT_QUERY, Integer.class, "%".concat(computerName + "%"));
		} catch (DataAccessException e) {
			logger.debug("Erreur sur search count :" + e.getMessage());
		}

		return counter;
	}

}
