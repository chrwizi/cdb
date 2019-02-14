package app.projetCdb.services;

import java.sql.SQLException;

import app.projetCdb.dao.ComputerDao;
import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Computer;

public class CreateComputerService implements ICreateComputerService {
	private ComputerDao computerDao;

	public ComputerDao getComputerDao() {
		return computerDao;
	}

	public void setComputerDao(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}



	public CreateComputerService(ComputerDao computerDao) {
		this.computerDao = computerDao;
	} 

	@Override
	public void createComputer(Computer computer) throws IDCompanyNotFoundException, SQLException {
		computerDao.add(computer);
	}

}
