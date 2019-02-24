package app.projetCdb.services;

import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;

public interface IComputerService {
	public List<Computer> getAll();
	public Optional<Computer> finById(Long id);
	void createComputer(Computer computer);
	void delete(Long id);
}
