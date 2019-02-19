package app.projetCdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;
import app.projetCdb.dao.ComputerDao;
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
		Optional<List<Computer>>computersOptional=computerDao.findAll();
		return (computersOptional.isPresent()?computersOptional.get():new ArrayList<Computer>());
	} 

}
