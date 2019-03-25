package cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdb.core.models.Computer;
import cdb.persistence.dao.ComputerDao;
import cdb.persistence.dao.IDCompanyNotFoundException;

/**
 * 
 * @author chris_moyikoulou
 *
 */ 
@Service("computerService")
public class ComputerService implements IComputerService {

	private ComputerDao computerDao;
	//@Autowired
	private IPageSelect pageComputers;
	private int defaultNbComputersByPage = 30;
	//
	Logger logger=LoggerFactory.getLogger(getClass());

	public ComputerService(@Autowired ComputerDao computerDao) throws SQLException {
		this.computerDao = computerDao;
		int nbComputersInDatabase = computerDao.count();
		pageComputers = new ComputersPage(0,
				(Integer.valueOf(nbComputersInDatabase) < defaultNbComputersByPage ? nbComputersInDatabase
						: defaultNbComputersByPage));
		pageComputers.setMaxResult(nbComputersInDatabase);
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
	public List<Computer> getAll(IPageSelect page) {
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = computerDao.findAll(page.getCurrentPage(), page.getOffset());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
	
	 

	
	@Override
	public List<Computer>getPageSortedByName(int num,boolean asc) throws SQLException {
		pageComputers.setCurrentPage(num);
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = computerDao.sortByName(asc,pageComputers.getCursor(), pageComputers.getOffset());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageComputers.setMaxResult(computerDao.count());
		return computers;
	}


	@Override
	public List<Computer> sortByCompanyName(boolean asc){
		List<Computer> sortedComputers=new ArrayList<Computer>();
		try {
			sortedComputers=computerDao.sortByName(asc,0,0);
		} catch (SQLException e) {
			logger.debug("erreur lors du sort by company");
		}
		return sortedComputers;
	}
	
	

	@Override
	public Optional<Computer> finById(Long id) throws SQLException {
		return this.computerDao.findById(id);
	} 

	@Override
	public void createComputer(Computer computer) {
		try {
			computerDao.add(computer);
		} catch ( SQLException e) {
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
	public List<Computer>getPage(int num) throws SQLException {
		pageComputers.setCurrentPage(num);
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			computers = computerDao.findAll( pageComputers.getOffset(),pageComputers.getCursor());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageComputers.setMaxResult(computerDao.count());
		return computers;
	}

	
	@Override
	public List<Computer> getPage(int num, String computerName) throws SQLException {
		pageComputers.setCurrentPage(num);
		List<Computer> computers =new ArrayList<Computer>();
		try {
			computers = computerDao.search(pageComputers.getOffset(),pageComputers.getCursor(), computerName);
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





}
