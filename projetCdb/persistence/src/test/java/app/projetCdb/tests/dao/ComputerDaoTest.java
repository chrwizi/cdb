package app.projetCdb.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cdb.core.models.Company;
import cdb.core.models.Computer;
import cdb.persistence.configuration.DbAccess;
import cdb.persistence.configuration.IDbAccess;
import cdb.persistence.dao.CompanyDao;
import cdb.persistence.dao.ComputerDao;
import cdb.persistence.dao.IDCompanyNotFoundException;

public class ComputerDaoTest {
	// access to database
	private static IDbAccess dbAccess;
	private static Properties testDatabaseProperties = new Properties();
	//
	private static ComputerDao computerDao;
	private static CompanyDao companyDao;
	//
	private static String DATABASE_CONFIGURATION_FILE = "database/databaseForTests.properties";
	private static List<Company> companies;
	//
	private static Logger logger=LoggerFactory.getLogger(ComputerDaoTest.class);
	
	@BeforeClass
	public static void setUp() {
		 
		try (FileInputStream input = new FileInputStream(DATABASE_CONFIGURATION_FILE)) {
			testDatabaseProperties.load(input);
			testDatabaseProperties.getProperty(DbAccess.getUrlPropertyName());
			testDatabaseProperties.getProperty(DbAccess.getPasswordPropertyName());
			testDatabaseProperties.getProperty(DbAccess.getUsernamePropertyName());

			dbAccess = DbAccess.getInstance(testDatabaseProperties);

//			computerDao = new ComputerDao(dbAccess);
//			companyDao = new CompanyDao(dbAccess);
			// create new companies in database
			companies = new ArrayList<Company>();
			companies.add(new Company("Dell"));
			companies.add(new Company("Hp"));
			companies.add(new Company("Windows"));
			for (Company company : companies) {
				OptionalLong optionalId = companyDao.Add(company);
				if (optionalId.isPresent()) {
					company.setId(optionalId.getAsLong());
				}
			}
			logger.info("setup for tests computerDao done ");
		} catch (FileNotFoundException e) {
			logger.error("database file config not found");
		} catch (IOException e) {
			logger.error("loading properties from database file config fail");
		}

	}

	
	
	@AfterClass
	public static void tearDown() {
//		// clean database
//		for (Company company : companies) {
//			// companyDao.delete(company.getId());
//		}
	}
	
	

	@Test
	public void testAddANewComputer() {
		// DEFINE
		Computer computer = new Computer(0L, "MonPc", LocalDate.of(2019, 3, 20),  LocalDate.of(2019, 4, 20), companies.get(0));
		OptionalLong id = OptionalLong.empty();
		// WHEN
		try {
			id = computerDao.add(computer);
			computer.setId(id.getAsLong());
			// THEN
			if (id.isPresent()) {
				Optional<Computer> optional = computerDao.findById(id.getAsLong());
				assertTrue(optional.isPresent());
				assertEquals(computer, optional.get());
				computerDao.delete(optional.get().getId());
				logger.info("Test addComputer done");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	@Test
	public void testUpdateComputer() {
		try {
			// DEFINE
			Computer computer = new Computer(0L, "MonPc", LocalDate.of(2019, 3, 20),  LocalDate.of(2019, 4, 20), companies.get(0));
			OptionalLong id = OptionalLong.empty();
			id = computerDao.add(computer);
			if (id.isPresent()) {
				computer.setId(id.getAsLong());
				// WHEN
				computer.setCompany(companies.get(1));
				computer.setName("TonPc");
				OptionalLong updatedIdComputer= computerDao.update(computer);
				//THEN
				if(updatedIdComputer.isPresent()) {
					Optional<Computer> optionalUpdatedComputer=computerDao.findById(updatedIdComputer.getAsLong());
					//assertTrue(optionalUpdatedComputer.isPresent());
					Computer updatedComputer=optionalUpdatedComputer.get();
					//assertEquals(updatedComputer.getCompany(),companies.get(1));
					
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IDCompanyNotFoundException e) {
			e.printStackTrace();
		}
	}
*/
	
	
	@Test
	public void testFindByIdWithExistingComputer() {
		// DEFINE
		Computer computer = new Computer(0L, "MonPc", LocalDate.of(2019, 3, 20),  LocalDate.of(2019, 4, 20), companies.get(0));
		OptionalLong id =OptionalLong.empty();
		// WHEN
		try {
			id = computerDao.add(computer);
			computer.setId(id.getAsLong());
			// THEN
			if (id.isPresent()) {
				System.out.println("id computer is present ");
				Optional<Computer> optional = computerDao.findById(id.getAsLong());
				assertTrue(optional.isPresent());
				assertEquals(computer,optional.get());
				computerDao.delete(optional.get().getId());
			}else System.out.println("id computer is not present ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindByIdWithUnexistingComputer() {

	}

}
