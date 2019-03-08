package app.projetCdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.projetCdb.models.Company;
import app.projetCdb.persistance.CompanyDao;
import app.projetCdb.persistance.DbAccess;

@Service("companyService")
public class CompanyService implements ICompanyServices {
	private CompanyDao CompanyDao;

	public CompanyService(@Autowired CompanyDao companyDao) {
		CompanyDao = companyDao;
	}

	public CompanyService() {
		CompanyDao = new CompanyDao(DbAccess.getInstance());
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
