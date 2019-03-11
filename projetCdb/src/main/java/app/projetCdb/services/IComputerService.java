package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;

public interface IComputerService {
	Optional<Computer> finById(Long id) throws SQLException;

	List<Computer> getPageSortedByName(int num, boolean asc) throws SQLException;

	List<Computer> getAll();

	List<Computer> getAll(IPageSelect page);

	List<Computer> sortByCompanyName(boolean asc);

	List<Computer> getPage(int num, String computerNa) throws SQLException;

	List<Computer> getPage(int num) throws SQLException;

	void createComputer(Computer computer);

	public void updateComputer(Computer computer);

	void delete(Long id);

	public int count() throws SQLException;

	int getNbPages();

	void delete(long[] idTab);

}
