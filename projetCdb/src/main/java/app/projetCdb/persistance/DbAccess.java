package app.projetCdb.persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DbAccess implements IDbAccess{
	
	private static DbAccess instance=new DbAccess();
	
	
	private static String URL="jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String LOGIN="admincdb";
	private static String PASSWORD="qwerty1234"; 
	
	
	//default database  configuration file
	private String DEFAULT_DATABASE_CONFIGURATION_FILE="database/database.properties";
	//properties from configuration file
	private static String URL_PROPERTIE="db_url";
	private static String USERNAME_PROPERTIE="db_username";
	private static String PASSWORD_PROPERTIE="db_password";
	
	
	
	private HikariDataSource hikariDataSource;
	private static int NB_POOL=10;
	private static final long TIME_OUT = 10000L;
	private Properties databaseProperties=new Properties();
	
	

	private DbAccess() { 
		try(FileInputStream input=new FileInputStream(DEFAULT_DATABASE_CONFIGURATION_FILE)){
			databaseProperties.load(input);
			/*ca marche */
			System.out.println(databaseProperties.values());
			System.out.println(databaseProperties.stringPropertyNames());
			
			
			databaseProperties.entrySet().forEach(o -> System.out.println(o.getKey() + "=" + o.getValue()));
			
			/*testt: renvoie un NullPointerException*/
			System.out.println(databaseProperties.getProperty(USERNAME_PROPERTIE));
			System.out.println(databaseProperties.getProperty(URL_PROPERTIE));
			
			/*renvoie un NullPointerException*/
			databaseProperties.getProperty(URL_PROPERTIE);
			databaseProperties.getProperty(USERNAME_PROPERTIE);
			databaseProperties.getProperty(PASSWORD_PROPERTIE);
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("\n\n>>>> fichier non trouvé <<<<<<<\n\n");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("\n\n>>>> IOException <<<<<<<\n\n");
			e.printStackTrace();
		}
	}
	
	private DbAccess(Properties dbProperties) { 
		databaseProperties=dbProperties;
	}
	
	
	private void setUpHikari() {
		final HikariConfig hikariConfig=new HikariConfig(databaseProperties);
/*		
		hikariConfig.setJdbcUrl(databaseProperties.getProperty(URL_PROPERTIE));
		hikariConfig.setUsername(databaseProperties.getProperty(USERNAME_PROPERTIE));
		hikariConfig.setPassword(databaseProperties.getProperty(PASSWORD_PROPERTIE));
		
		hikariConfig.setMaximumPoolSize(NB_POOL);
		hikariConfig.setConnectionTimeout(TIME_OUT);*/
		this.hikariDataSource=new HikariDataSource(hikariConfig);
	}

	public static DbAccess getInstance() {
		return (instance == null) ? new DbAccess() : instance;
	}
	
	public static DbAccess getInstance(Properties databaseProperties) {
		return (instance == null) ? new DbAccess(databaseProperties) : instance;
	}


	@Override
	public Connection getConnection() throws SQLException {
		if(hikariDataSource==null) {
			setUpHikari();
		}
		return hikariDataSource.getConnection();
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
