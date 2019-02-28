package app.projetCdb.tests.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.CompanyDao;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.persistance.IDbAccess;
import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Company;

public class ComputerDaoTest {
	// access to database
	private static IDbAccess dbAccess;
	private static Properties testDatabaseProperties = new Properties();
	//
	private static ComputerDao computerDao;
	private static CompanyDao companyDao;
	

	//
	private static String DATABASE_CONFIGURATION_FILE = "database/databaseForTest.properties";
	private static List<Company> companies;

	@BeforeClass
	public static void setUp() {

		try (FileInputStream input = new FileInputStream(DATABASE_CONFIGURATION_FILE)) {
			testDatabaseProperties.load(input);
			testDatabaseProperties.getProperty(DbAccess.getUrlPropertyName());
			testDatabaseProperties.getProperty(DbAccess.getPasswordPropertyName());
			testDatabaseProperties.getProperty(DbAccess.getUsernamePropertyName());

			dbAccess = DbAccess.getInstance(testDatabaseProperties);
			
			computerDao = new ComputerDao(dbAccess);
			companyDao = new CompanyDao(dbAccess);
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public static void tearDown() {
		// clean database
		for (Company company : companies) {
			// companyDao.delete(company.getId());
		}
	}

	@Test
	public void testAddANewComputer() {
		// DEFINE
		Computer computer = new Computer(0L, "MonPc", new Date(), new Date(), companies.get(0));
		OptionalLong id = null;
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IDCompanyNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateComputer() {
		try {
			// DEFINE
			Computer computer = new Computer(0L, "MonPc", new Date(), new Date(), companies.get(0));
			OptionalLong id = OptionalLong.empty();
			id = computerDao.add(computer);
			if (id.isPresent()) {
				computer.setId(id.getAsLong());
				// WHEN
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IDCompanyNotFoundException e) {
			e.printStackTrace();
		}

		// THEN

	}

	@Test
	public void testFindByIdWithExistingComputer() {
		// DEFINE
		Computer computer = new Computer(0L, "MonPc", new Date(), new Date(), companies.get(0));
		OptionalLong id = null;

		// WHEN
		try {
			id = computerDao.add(computer);
			computer.setId(id.getAsLong());
			// THEN
			if (id.isPresent()) {
				Optional<Computer> optional = computerDao.findById(id.getAsLong());
				// THEN
				assertTrue(optional.isPresent());
				assertEquals(computer, optional.get());
				computerDao.delete(optional.get().getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IDCompanyNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindByIdWithUnexistingComputer() {

	}

}
