package app.projetCdb.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
	@Bean
	private HikariDataSource dataSource(@Autowired CdbDataSourceProperties properties) {
		return (HikariDataSource) DataSourceBuilder.create().type(HikariDataSource.class).url(properties.getUrl())
				.username(properties.getUsername()).password(properties.getPassword()).build();
	}
}
