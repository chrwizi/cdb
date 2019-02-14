package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;

import app.projetCdb.models.Company;

public interface IListCompaniesService {
	public List<Company> getAll() throws SQLException;
}
