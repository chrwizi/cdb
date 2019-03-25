package app.projetCdb.tests.dao;

import org.junit.BeforeClass;
import org.junit.Test;

import cdb.persistence.dao.CompanyDao;
import cdb.persistence.dao.ComputerDao;
import cdb.persistence.dao.DbAccess;
import cdb.persistence.dao.IDbAccess;

public class CompanyDaoTest {
	private static ComputerDao computerDao;
	private static CompanyDao companyDao;
	private static IDbAccess dbAccess;
	
	@BeforeClass
	public void setUp() {
		dbAccess=DbAccess.getInstance();
	}
	
	
	@Test
	public void testAdd() {
		
	}
	

}
