package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;

public interface IComputerService {
	public List<Computer> getAll() throws SQLException;
	public Optional<Computer> finById(Long id);
}
