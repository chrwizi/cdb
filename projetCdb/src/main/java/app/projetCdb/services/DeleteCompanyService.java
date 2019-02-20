package app.projetCdb.services;

import java.sql.SQLException;


public class DeleteCompanyService implements IDeleteService {
	private app.projetCdb.persistance.CompanyDao CompanyDao;

	public DeleteCompanyService(app.projetCdb.persistance.CompanyDao companyDao) {
		super();
		CompanyDao = companyDao;
	}

	public app.projetCdb.persistance.CompanyDao getCompanyDao() {
		return CompanyDao;
	}

	public void setCompanyDao(app.projetCdb.persistance.CompanyDao companyDao) {
		CompanyDao = companyDao;
	}

	@Override
	public void delete(Long id) throws SQLException {
		CompanyDao.delete(id);
	}

}
