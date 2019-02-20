package app.projetCdb.tests.dao;

import org.junit.BeforeClass;
import org.junit.Test;

import app.projetCdb.persistance.CompanyDao;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.persistance.IDbAccess;

public class CompanyDaoTest {
	private static ComputerDao computerDao;
	private static CompanyDao companyDao;
	private static IDbAccess dbAccess;
	
	@BeforeClass
	public void setUp() {
		dbAccess=DbAccess.getInstance();
		companyDao=new CompanyDao(dbAccess);
		computerDao=new ComputerDao(dbAccess);
	}
	
	
	@Test
	public void testAdd() {
		
	}
	

}
