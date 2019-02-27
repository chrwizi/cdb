package app.projetCdb.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DbAccess implements IDbAccess{
	
	private static DbAccess instance=new DbAccess();
	private static String URL="jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String LOGIN="admincdb";
	private static String PASSWORD="qwerty1234"; 
	private HikariDataSource hikariDataSource;
	private static int NB_POOL=10;
	private static final long TIME_OUT = 10000L;

	private DbAccess() { 
		super();
	}

	public static DbAccess getInstance() {
		return (instance == null) ? new DbAccess() : instance;
	}


	@Override
	public Connection getConnection() throws SQLException {
		if(hikariDataSource==null) {
			setUpHikari();
		}
		return hikariDataSource.getConnection();
	}
	
	
	private void setUpHikari() {
		final HikariConfig hikariConfig=new HikariConfig();
		hikariConfig.setJdbcUrl(URL);
		hikariConfig.setUsername(LOGIN);
		hikariConfig.setPassword(PASSWORD);
		hikariConfig.setMaximumPoolSize(NB_POOL);
		hikariConfig.setConnectionTimeout(TIME_OUT);
		this.hikariDataSource=new HikariDataSource(hikariConfig);
	}
	
	
	public void initPool() {
		setUpHikari();
	}
	
	public void closePool() {
		this.hikariDataSource.close();
	}
	
	



	public static String getUrl() {
		return URL;
	}

	public static String getLogin() {
		return LOGIN;
	}

	public static String getPassword() {
		return PASSWORD;
	}


	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public static String getLOGIN() {
		return LOGIN;
	}

	public static void setLOGIN(String lOGIN) {
		LOGIN = lOGIN;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	

	
	
	
}
