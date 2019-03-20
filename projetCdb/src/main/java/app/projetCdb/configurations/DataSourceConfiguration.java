package app.projetCdb.configurations;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
//@EnableTransactionManagement
public class DataSourceConfiguration {
	private DataSourceProperties properties;

	private Environment env;
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    
	public DataSourceConfiguration(DataSourceProperties properties, Environment environement) {
		this.properties = properties;
		this.env = environement;
	}

	/*
	 * @Bean public DataSource dataSource() { System.out.println("\n\n>>> username "
	 * + properties.getUsername() + " <<<<\n\n"); return
	 * DataSourceBuilder.create().url(properties.getUrl()).driverClassName(
	 * properties.getDriver())
	 * .username(properties.getUsername()).password(properties.getPassword()).build(
	 * ); }
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		// dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(properties.getUrl());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());

		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("app.projetCdb.models");
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	   private Properties hibProperties() {
	        Properties properties = new Properties();
	        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
	        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
	        return properties;  
	    }

	/*
	 * @Bean public LocalSessionFactoryBean sessionFactory() {
	 * LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	 * sessionFactory.setDataSource(dataSource());
	 * sessionFactory.setPackagesToScan("app.projetCdb.models");
	 * sessionFactory.setHibernateProperties(hibernateProperties());
	 * 
	 * return sessionFactory; }
	 */

	/*
	 * @Bean public DataSource restDataSource() { BasicDataSource dataSource = new
	 * BasicDataSource();
	 * dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	 * dataSource.setUrl(env.getProperty("jdbc.url"));
	 * dataSource.setUsername(env.getProperty("jdbc.user"));
	 * dataSource.setPassword(env.getProperty("jdbc.pass"));
	 * 
	 * return dataSource; }
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/*
	 * @Bean public LocalSessionFactoryBean sessionFactory() {
	 * LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	 * System.out.println(dataSource()); sessionFactory.setDataSource(dataSource());
	 * sessionFactory.setPackagesToScan("app.projetCdb.models");
	 * sessionFactory.setHibernateProperties(hibernateProperties()); return
	 * sessionFactory; }
	 */
	Properties hibernateProperties() {
		return new Properties() {
			{
				// setProperty("hibernate.hbm2ddl.auto",
				// env.getProperty("hibernate.hbm2ddl.auto"));
				setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
				setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
	}

	/*
	 * @Bean public DataSource dataSource() { HikariConfig config=new
	 * HikariConfig(); config.setJdbcUrl(properties.getUrl());
	 * config.setUsername(properties.getUsername());
	 * config.setPassword(properties.getPassword()); DataSource datasource=new
	 * HikariDataSource(config); return datasource; }
	 */
	/*
	 * @Bean public LocalSessionFactoryBean sessionFactory() {
	 * LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	 * System.out.println(dataSource()); sessionFactory.setDataSource(dataSource());
	 * sessionFactory.setPackagesToScan("app.projetCdb.models");
	 * sessionFactory.setHibernateProperties(hibernateProperties()); return
	 * sessionFactory; }
	 * 
	 * @Bean
	 * 
	 * @Autowired public HibernateTransactionManager
	 * hibernateTransactionManager(SessionFactory sessionFactory) {
	 * HibernateTransactionManager hibernateTransactionManager = new
	 * HibernateTransactionManager();
	 * hibernateTransactionManager.setSessionFactory(sessionFactory);
	 * 
	 * return hibernateTransactionManager;
	 * 
	 * }
	 * 
	 * private final Properties hibernateProperties() { Properties
	 * hibernateProperties = new Properties();
	 * hibernateProperties.setProperty("hibernate.dialect",
	 * "org.hibernate.dialect.MySQL5Dialect");
	 * hibernateProperties.setProperty("hibernate.show_sql", "false"); //
	 * hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update"); return
	 * hibernateProperties; }
	 * 
	 * @Bean public PersistenceExceptionTranslationPostProcessor
	 * exceptionTranslation() { return new
	 * PersistenceExceptionTranslationPostProcessor(); }
	 */
}
