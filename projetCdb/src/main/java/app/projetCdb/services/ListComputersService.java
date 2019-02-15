package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;
import app.projetCdb.dao.ComputerDao;
import app.projetCdb.models.Computer;
/**
 * 
 * @author chris_moyikoulou
 *
 */
public class ListComputersService implements IListComputersService {
	private ComputerDao computerDao;

	public ListComputersService(ComputerDao computerDao) {
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
	/**
	 * @return list of all computers in database
	 */
	public List<Computer> getAll() throws SQLException {
		System.out.println("inside getAll");
		return computerDao.findAll();
	}

}