package cdb.persistence.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {
	private DataSourceProperties properties;
	private final String PACKAGES_TO_SCAN="cdb.core.models";

	public DataSourceConfiguration(DataSourceProperties properties) {
		System.out.println("\n\n >>>>>>>> config Datasource  <<<<<<\n\n\n");
		this.properties = properties;
	}

	@Bean("datasource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(properties.getUrl());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());
		//dataSource.setDriverClassName(properties.getDriver());
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		return sessionFactoryBean;
	}


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


	Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
				setProperty("hibernate.globally_quoted_identifiers", "true");
			//	setProperty("hibernate.globally_quoted_identifiers", "update");
			}
		};
	}


}
