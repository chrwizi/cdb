package app.projetCdb.services;

import java.sql.SQLException;

import app.projetCdb.dao.ComputerDao;


public class DeleteComputerService implements IDeleteService {
	private ComputerDao computerDao;

	public DeleteComputerService(ComputerDao computerDao) {
		super();
		this.computerDao = computerDao;
	}

	public ComputerDao getComputerDao() {
		return computerDao;
	}

	public void setComputerDao(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}

	@Override
	public void delete(Long id) throws SQLException {
		computerDao.delete(id);
	}

}
