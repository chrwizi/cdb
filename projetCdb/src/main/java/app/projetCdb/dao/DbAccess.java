package app.projetCdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess implements IDbAccess{
	private static DbAccess instance=new DbAccess();
	private final static String DRIVER="com.mysql.cj.jdbc.Driver";
	private final static String URL="jdbc:mysql://localhost:3306/computer-database-db";
	private final static String LOGIN="root";
	private final static String PASSWORD="";

	private DbAccess() {
		super();
	}

	public static DbAccess getInstance() {
		return (instance==null)?new DbAccess():instance;
	}

	public static void loadDriver() throws ClassNotFoundException {
		Class.forName(DRIVER);
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL,LOGIN,PASSWORD);
	}
}
