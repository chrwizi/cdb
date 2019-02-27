package app.projetCdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Company;
import app.projetCdb.persistance.CompanyDao;
import app.projetCdb.persistance.DbAccess;

public class CompanyService implements ICompanyServices {
	private CompanyDao CompanyDao;

	public CompanyService(CompanyDao companyDao) {
		CompanyDao = companyDao;
	}
	public CompanyService() {
		CompanyDao =new CompanyDao(DbAccess.getInstance());
	}

	public CompanyDao getCompanyDao() {
		return CompanyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		CompanyDao = companyDao;
	}

	@Override
	public Optional<List<Company>> getAll(){
		Optional<List<Company>> optional=Optional.empty();
		List<Company> companies = new ArrayList<>();
		try {
			companies = CompanyDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (!companies.isEmpty())?Optional.of(companies):optional;
	}
	
  
	@Override
	public void delete(Long id) throws SQLException {
		CompanyDao.delete(id);
	}

	@Override
	public Optional<Company> findById(Long id) {
		return CompanyDao.findById(id);
	}
	



}
