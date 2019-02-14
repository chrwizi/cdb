package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;

import app.projetCdb.dao.CompanyDao;
import app.projetCdb.models.Company;

public class ListCompaniesService implements IListCompaniesService{
	private CompanyDao CompanyDao;
	
	
	public ListCompaniesService(CompanyDao companyDao) {
		super();
		CompanyDao = companyDao;
	}
	
	public CompanyDao getCompanyDao() {
		return CompanyDao;
	}
	
	public void setCompanyDao(CompanyDao companyDao) {
		CompanyDao = companyDao;
	}	
 
	@Override
	public List<Company> getAll() throws SQLException {
		return CompanyDao.findAll();
	}

}
