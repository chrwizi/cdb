package app.projetCdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
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
	public List<Computer> getAll() {
		Optional<List<Computer>> computersOptional=Optional.empty();
		try {
			computersOptional = computerDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (computersOptional.isPresent()?computersOptional.get():new ArrayList<Computer>());
	}

	@Override
	public Optional<Computer> finById(Long id) {
		return this.computerDao.findById(id);
	}
	
	@Override
	public void createComputer(Computer computer) {
		try {
			computerDao.add(computer);
		} catch (IDCompanyNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void delete(Long id){
		try {
			computerDao.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
