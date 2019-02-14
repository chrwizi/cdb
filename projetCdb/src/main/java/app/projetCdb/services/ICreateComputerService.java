package app.projetCdb.services;

import java.sql.SQLException;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Computer;

public interface ICreateComputerService {
	public void createComputer(Computer computer)throws IDCompanyNotFoundException, SQLException;
}
