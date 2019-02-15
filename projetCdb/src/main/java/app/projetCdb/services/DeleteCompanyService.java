package app.projetCdb.services;

import java.sql.SQLException;
import app.projetCdb.dao.CompanyDao;

public class DeleteCompanyService implements IDeleteService {
	private CompanyDao CompanyDao;

	public DeleteCompanyService(CompanyDao companyDao) {
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
	public void delete(Long id) throws SQLException {
		CompanyDao.delete(id);
	}

}
