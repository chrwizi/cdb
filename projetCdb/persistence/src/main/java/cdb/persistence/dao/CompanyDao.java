package cdb.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import javax.persistence.criteria.CriteriaBuilder;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import cdb.core.models.Company;

@Repository
public class CompanyDao {
	// access to database
	private DataSource datasource;
	private JdbcTemplate jdbcTemplate;
	private LocalSessionFactoryBean sessionFactory;
	private CriteriaBuilder criteriaBuilder;

	/* Name of table */
	private final static String TABLE = "company";
	private final static String COMPUTER_TABLE = ComputerDao.getTable();
	private final static String COMPANY_ID_IN_COMPUTER_TABLE = ComputerDao.getForignKeyCompanyId();
	/* fields of table */
	private final static String FIELD_1 = "id";
	private final static String FIELD_2 = "name";
	/* queries */
	private final static String DELETE_COMPANY_QUERY = "DELETE FROM " + TABLE + " WHERE " + FIELD_1 + "=?";
	private final static String DELETE_ASSOCIATED_COMPUTERS = "DELETE  FROM " + COMPUTER_TABLE + " WHERE "
			+ COMPANY_ID_IN_COMPUTER_TABLE + " =?";
	// private final static String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE + "
	// WHERE " + FIELD_1 + "=?";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public CompanyDao(DataSource dbAccess, LocalSessionFactoryBean sessionFactory) {
		this.datasource = dbAccess;
		this.sessionFactory = sessionFactory;
		this.jdbcTemplate = new JdbcTemplate(dbAccess);
	}

	// company mapper from database
	private RowMapper<Company> companyMapper = (resultSet, rowNum) -> new Company(resultSet.getLong(FIELD_1),
			resultSet.getString(FIELD_2));

	/**
	 * 
	 * @param compagny company to add in companies table
	 * @return
	 * @throws SQLException if connection database failure
	 */
	public OptionalLong Add(Company company) {
		OptionalLong optionalId = OptionalLong.empty();
		if (company != null) {
			System.out.println("in add Company");
			try (Session session = sessionFactory.getObject().openSession()) {
				/*
				 * Transaction transaction = session.beginTransaction();
				 * session.persist(company); transaction.commit();
				 */
				System.out.println("in add Company try");
				Long id=(Long) session.save(company);
				optionalId = OptionalLong.of(company.getId());
				System.out.println("in add Company Saved");
			} catch (HibernateException e) {
				logger.debug("Erreur sur add computer : " + e.getMessage());
			}
		}
		return optionalId;
	}

	/**
	 * 
	 * @param company update compagny int table
	 * @throws SQLException if connection to database failure
	 */
	public void update(Company company) {
		try (Session session = sessionFactory.getObject().openSession()) {
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaUpdate<Company> updateCriteria = criteriaBuilder.createCriteriaUpdate(Company.class);
			Root<Company> root = updateCriteria.from(Company.class);
			updateCriteria.set(FIELD_2, company.getName());
			updateCriteria.where(criteriaBuilder.equal(root.get(FIELD_1), company.getId()));
			Transaction transaction = session.beginTransaction();
			session.createQuery(updateCriteria).executeUpdate();
			transaction.commit();

		} catch (HibernateException e) {
			logger.debug("Erreur sur update company : " + e.getMessage());
		}
	}

	public Optional<Company> findById(Long id) {
		Optional<Company> optional = Optional.empty();
		if (id != null) {

			try (Session session = sessionFactory.getObject().openSession()) {
				criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Company> findCriteria = criteriaBuilder.createQuery(Company.class);
				Root<Company> root = findCriteria.from(Company.class);
				findCriteria.select(root).where(criteriaBuilder.equal(root.get(FIELD_1), id));
				Query<Company> query = session.createQuery(findCriteria);
				optional = query.uniqueResultOptional();
			} catch (HibernateException e) {
				logger.debug("erreur find company by Id : " + e.getMessage());
			}
		}
		return optional;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Company> findAll() throws SQLException {
		List<Company> companies = new ArrayList<Company>();

		try (Session session = sessionFactory.getObject().openSession()) {
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Company> findAllCriteria = criteriaBuilder.createQuery(Company.class);
			Root<Company> rootCompany = findAllCriteria.from(Company.class);
			findAllCriteria.select(rootCompany);
			Query<Company> query = session.createQuery(findAllCriteria);
			companies = query.getResultList();
		} catch (HibernateException e) {
			logger.debug("Erreur sur find All companies : " + e.getMessage());
		}

		return companies;
	}

	/**
	 * 
	 * @param id / id of company to delete in table and associated computers
	 * @throws SQLException if connection to database failure
	 */
	public void delete(Long id) {
		try (Connection connection = datasource.getConnection()) {
			// prepare statements
			PreparedStatement deleteCompanyPStatement = connection.prepareStatement(DELETE_COMPANY_QUERY);
			PreparedStatement deleteComputersPStatement = connection.prepareStatement(DELETE_ASSOCIATED_COMPUTERS);
			deleteCompanyPStatement.setLong(1, id);
			deleteComputersPStatement.setLong(1, id);
			// begin transaction
			connection.setAutoCommit(false);
			try {
				logger.debug("début de la transaction de suppression");
				deleteComputersPStatement.executeUpdate();
				logger.debug("entre 2 stmnt");
				deleteCompanyPStatement.executeUpdate();
				connection.commit();
				logger.debug("suppression d'une companie effectuée");
			} catch (SQLException e) {
				connection.rollback();
				logger.debug("transaction de supression de companie annulée");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			logger.debug("échec de connexion à la base de données ");
		}
	}



}
