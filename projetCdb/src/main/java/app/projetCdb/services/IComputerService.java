package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Computer;

public interface IComputerService {
	public List<Computer> getAll() throws SQLException;
	public Optional<Computer> finById(Long id);
	void createComputer(Computer computer) throws IDCompanyNotFoundException, SQLException;
	void delete(Long id) throws SQLException;
}
