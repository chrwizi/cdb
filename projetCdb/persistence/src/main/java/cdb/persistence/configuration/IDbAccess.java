package cdb.persistence.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface IDbAccess {
	public Connection getConnection() throws SQLException;

	DataSource getDatasource();
}
