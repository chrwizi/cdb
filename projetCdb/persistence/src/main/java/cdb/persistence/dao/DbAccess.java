package cdb.persistence.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Service("dbAccess")
public class DbAccess implements IDbAccess {
	// default database configuration file
	private String DEFAULT_DATABASE_CONFIGURATION_FILE = "database/application.properties";
	private static Properties databaseProperties = new Properties();
	// properties from configuration file
	private static String URL_PROPERTIE_NAME = "datasource.url";
	private static String USERNAME_PROPERTIE_NAME = "datasource.username";
	private static String PASSWORD_PROPERTIE_NAME = "datasource.password";
	// hiraki properties
	private HikariDataSource hikariDataSource;
	private static int NB_POOL = 2;
	private static final long TIME_OUT = 10000L;
	// logger
	private Logger logger = LoggerFactory.getLogger(DbAccess.class);
	// singleton instance
	private static DbAccess instance = new DbAccess();
	
	/**
	 * @throws DbAccessPropertyNotFoundException 
	 *  
	 */
	public DbAccess(){
		try (FileInputStream input = new FileInputStream(DEFAULT_DATABASE_CONFIGURATION_FILE)) {
			databaseProperties.load(input);
			if((databaseProperties.getProperty(URL_PROPERTIE_NAME))==null) logger.debug("Property "+URL_PROPERTIE_NAME+" not found");
			if((databaseProperties.getProperty(USERNAME_PROPERTIE_NAME))==null) logger.debug("Property "+URL_PROPERTIE_NAME+" not found");
			if((databaseProperties.getProperty(PASSWORD_PROPERTIE_NAME))==null)logger.debug("Property "+PASSWORD_PROPERTIE_NAME+" not found");
			
			logger.info("[database properties loaded ]");
		} catch (FileNotFoundException e) {
			logger.error("[file configuration of database  not found ]");  
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("[IOException form file configuration]");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param dbProperties
	 */
	public DbAccess(Properties dbProperties) {
		databaseProperties = dbProperties;
	}

	private void setUpHikari() {
		final HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(databaseProperties.getProperty(URL_PROPERTIE_NAME));
		hikariConfig.setUsername(databaseProperties.getProperty(USERNAME_PROPERTIE_NAME));
		hikariConfig.setPassword(databaseProperties.getProperty(PASSWORD_PROPERTIE_NAME));
		hikariConfig.setMaximumPoolSize(NB_POOL);
		hikariConfig.setConnectionTimeout(TIME_OUT);
		this.hikariDataSource = new HikariDataSource(hikariConfig);
		logger.info("[hikari data source configurated]");
	}

	public static DbAccess getInstance() {
		return (instance == null) ? new DbAccess() : instance;
	}

	
	public static DbAccess getInstance(Properties dbProperties) {
		if (instance == null) {
			return new DbAccess(dbProperties);
		}
		databaseProperties=dbProperties;
		return instance;
	} 

	@Override
	public Connection getConnection(){
		if (hikariDataSource == null) {
			setUpHikari();
		}
		
		Connection connection = null;
		
		try {
			connection = hikariDataSource.getConnection();
			logger.debug("connexion établie avec la base de données");
		} catch (SQLException e) {
			logger.debug("echec de connexion à la base de données");
		}
		
		return  connection;
	}
	
	

	@Override
	public DataSource getDatasource() {
		if (hikariDataSource == null) {
			setUpHikari();
		}
		return  hikariDataSource;
	}
	
	public void initPool() {
		setUpHikari();
	}
 
	public void closePool() {
		this.hikariDataSource.close();
	}

	public static String getUrlPropertyName() {
		return URL_PROPERTIE_NAME;
	}

	public static String getUsernamePropertyName() {
		return USERNAME_PROPERTIE_NAME;
	}

	public static String getPasswordPropertyName() {
		return PASSWORD_PROPERTIE_NAME;
	}

}
