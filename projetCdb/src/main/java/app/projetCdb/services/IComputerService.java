package app.projetCdb.services;

import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.IPageSelect;

public interface IComputerService {
	public List<Computer> getAll();
	public  Optional<List<Computer>> getAll(IPageSelect page);
	public Optional<Computer> finById(Long id);
	void createComputer(Computer computer);
	public void updateComputer(Computer computer);
	void delete(Long id);
	public int count();
	public Optional<List<Computer>> getPage(int num);
	int getNbPages();
}
