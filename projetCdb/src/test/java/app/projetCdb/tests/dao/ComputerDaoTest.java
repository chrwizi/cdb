package app.projetCdb.tests.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalLong;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import app.projetCdb.models.Computer;

import app.projetCdb.dao.CompanyDao;
import app.projetCdb.dao.ComputerDao;
import app.projetCdb.dao.DbAccess;
import app.projetCdb.dao.IDbAccess;
import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Company;

public class ComputerDaoTest {
	//access to database 
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
		dbAccess=DbAccess.getInstance();
		computerDao=new ComputerDao(dbAccess);
		companyDao=new CompanyDao(dbAccess);
		//create new companies in database 
		companies= new ArrayList<Company>();
		companies.add(new Company("Dell"));
		companies.add(new Company("Hp"));
		companies.add(new Company("Windows"));
		for (Company company : companies) {
			 OptionalLong optionalId=companyDao.Add(company);
			 if(optionalId.isPresent()) {
				 company.setId(optionalId.getAsLong());
			 }
		}
	}
	
	@AfterClass
	public static void tearDown() {
		//clean database 
		for (Company company : companies) {
			companyDao.delete(company.getId());
		}
	}
	
	

	@Test
	public void testAddANewComputer() {
		// DEFINE
		Computer computer= Mockito.mock(Computer.class);
		Mockito.when(computer.getId()).thenReturn(0L);
		Mockito.when(computer.getName()).thenReturn("MonPc");
		Mockito.when(computer.getIntroduced()).thenReturn(new Date());
		Mockito.when(computer.getDiscontinued()).thenReturn(new Date());
		Mockito.when(computer.getCompany_id()).thenReturn(companies.get(0).getId());
		// WHEN
		try {
			computerDao.add(computer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IDCompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// THEN
		

	}

}
