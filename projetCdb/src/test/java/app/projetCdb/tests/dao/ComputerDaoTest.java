package app.projetCdb.tests.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

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
	private static ComputerDao computerDao;
	private static CompanyDao companyDao;
	private static IDbAccess dbAccess;
	private static String URL_FOR_TESTS = "jdbc:mysql://localhost:3306/computer-database-db_test?zeroDateTimeBehavior=convertToNull";
	private static String PASSWORD = "";
	private static String LOGIN = "root";
	//
	private static List<Company> companies;

	@BeforeClass
	public static void setUp() {
		DbAccess.setURL(URL_FOR_TESTS);
		DbAccess.setLOGIN(LOGIN);
		DbAccess.setPASSWORD(PASSWORD);
		dbAccess = DbAccess.getInstance();
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
		Computer computer = new Computer(0L, "MonPc", new Date(), new Date(), companies.get(0).getId());
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

}
