package app.projetCdb.services;

import java.sql.SQLException;


public class DeleteCompanyService implements IDeleteService {
	private app.projetCdb.dao.CompanyDao CompanyDao;

	public DeleteCompanyService(app.projetCdb.dao.CompanyDao companyDao) {
		super();
		CompanyDao = companyDao;
	}

	public app.projetCdb.dao.CompanyDao getCompanyDao() {
		return CompanyDao;
	}

	public void setCompanyDao(app.projetCdb.dao.CompanyDao companyDao) {
		CompanyDao = companyDao;
	}

	@Override
	public void delete(Long id) throws SQLException {
		CompanyDao.delete(id);
	}

}
