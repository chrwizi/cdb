package app.projetCdb.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbAccess implements IDbAccess{
	private static DbAccess instance=new DbAccess();
	private static String DRIVER="com.mysql.cj.jdbc.Driver";
	private static String URL="jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String LOGIN="admincdb";
	private static String PASSWORD="qwerty1234"; 

	private DbAccess() { 
		super();
	}

	public static DbAccess getInstance() {
		return (instance == null) ? new DbAccess() : instance;
	}

	public static void loadDriver() throws ClassNotFoundException {
		Class.forName(DRIVER);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, LOGIN, PASSWORD);
	}

	public static String getDriver() {
		return DRIVER;
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

	public static String getDRIVER() {
		return DRIVER;
	}

	public static void setDRIVER(String dRIVER) {
		DRIVER = dRIVER;
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
