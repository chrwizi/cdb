package cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cdb.core.models.Company;
import cdb.persistence.dao.CompanyDao;

@Service("companyService")
public class CompanyService implements ICompanyServices {
	private CompanyDao CompanyDao;

	public CompanyService(@Autowired CompanyDao companyDao) {
		CompanyDao = companyDao;
	}
 
	public CompanyDao getCompanyDao() {
		return CompanyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		CompanyDao = companyDao;
	}

	@Override
	public List<Company> getAll() {
		List<Company> companies = new ArrayList<>();
		try {
			companies = CompanyDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
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
