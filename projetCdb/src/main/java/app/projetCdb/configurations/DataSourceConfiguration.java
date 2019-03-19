package app.projetCdb.configurations;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class DataSourceConfiguration {
	@Bean
	public DataSource dataSource(CdbDataSourceProperties properties) {
		return DataSourceBuilder.create().url(properties.getUrl())
				.username(properties.getUsername()).password(properties.getPassword()).build();
	}
} 
 