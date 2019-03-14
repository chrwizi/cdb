package app.projetCdb.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfig {
	@Autowired
	private Environment environement;
	
	@Bean
	private DataSource dataSource() {
		
		return null;
	}
}
