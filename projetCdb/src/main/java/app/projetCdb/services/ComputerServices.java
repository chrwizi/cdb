package app.projetCdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.ComputerDao;
/**
 * 
 * @author chris_moyikoulou
 *
 */
public class ComputerServices implements IComputerService {
	private ComputerDao computerDao;

	public ComputerServices(ComputerDao computerDao) {
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

	@Override
	public Optional<Computer> finById(Long id) {
		return this.computerDao.findById(id);
	}

	

}
