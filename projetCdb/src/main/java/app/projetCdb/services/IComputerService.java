package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.IPageSelect;

public interface IComputerService {
	public List<Computer> getAll();
	public  Optional<List<Computer>> getAll(IPageSelect page);
	public Optional<Computer> finById(Long id) throws SQLException;
	void createComputer(Computer computer);
	public void updateComputer(Computer computer);
	void delete(Long id);
	public int count() throws SQLException;
	public Optional<List<Computer>> getPage(int num) throws SQLException;
	int getNbPages();
	Optional<List<Computer>> getPage(int num, String computerNa) throws SQLException;
	void delete(long[] idTab);
}
