package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Computer;

public interface IListComputersService {
	public List<Computer> getAll() throws SQLException;
}
