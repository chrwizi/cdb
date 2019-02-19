package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDbAccess {
	public Connection getConnection() throws SQLException;
}
