package app.projetCdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Computer;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.ComputersPage;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.persistance.IPageSelect;

/**
 * 
 * @author chris_moyikoulou
 *
 */
@Service("computerService")
public class ComputerService implements IComputerService {

	private ComputerDao computerDao;
	private IPageSelect pageComputers;
	private int defaultNbComputersByPage = 30;
	
	public ComputerService(@Autowired  ComputerDao computerDao) throws SQLException {
		this.computerDao = computerDao;
		int nbComputersInDatabase = computerDao.count();
		pageComputers = new ComputersPage(0,
				(Integer.valueOf(nbComputersInDatabase) < defaultNbComputersByPage ? nbComputersInDatabase
						: defaultNbComputersByPage));
		pageComputers.setMaxResult(nbComputersInDatabase);

	}



	public IPageSelect getPageComputers() {
		return pageComputers;
	}

	public void setPageComputers(IPageSelect pageComputers) {
		this.pageComputers = pageComputers;
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
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = computerDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	@Override
	public Optional<List<Computer>> getAll(IPageSelect page) {
		Optional<List<Computer>> computers = Optional.empty();
		try {
			computers = computerDao.findAll(page.getCurrentPage(), page.getOffset());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	@Override
	public Optional<Computer> finById(Long id) throws SQLException {
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
	public void delete(Long id) {
		try {
			computerDao.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(long idTab[]) {
		for (long id : idTab) {
			try {
				computerDao.delete(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateComputer(Computer computer) {
		try {
			computerDao.update(computer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int count() throws SQLException {
		return computerDao.count();
	}

	@Override
	public Optional<List<Computer>> getPage(int num) throws SQLException {
		pageComputers.setCurrentPage(num);
		Optional<List<Computer>> computers = Optional.empty();
		try {
			computers = computerDao.findAll(pageComputers.getCursor(), pageComputers.getOffset());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// update number max of result
		pageComputers.setMaxResult(computerDao.count());
		return computers;
	}

	@Override
	public Optional<List<Computer>> getPage(int num, String computerName) throws SQLException {
		pageComputers.setCurrentPage(num);
		Optional<List<Computer>> computers = Optional.empty();
		try {
			computers = computerDao.search(pageComputers.getCursor(), pageComputers.getOffset(), computerName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageComputers.setMaxResult(computerDao.seachcount(computerName));
		return computers;
	}

	@Override
	public int getNbPages() {
		return pageComputers.getNbPages();
	}

}
