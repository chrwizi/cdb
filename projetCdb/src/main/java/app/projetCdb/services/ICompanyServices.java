package app.projetCdb.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Company;

public interface ICompanyServices {
	public List<Company> getAll();
	public void delete(Long id) throws SQLException;
	public Optional<Company> findById(Long id);
}
 