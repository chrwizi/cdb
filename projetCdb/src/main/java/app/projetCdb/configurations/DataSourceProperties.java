package app.projetCdb.configurations;

import org.springframework.stereotype.Component;

@Component
//@PropertySource(value= {"classpath:resources/application.properties"})
//public class DataSourceProperties implements WebMvcConfigurer {
public class DataSourceProperties {
	//@Value("${datasource.url}")
	private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//@Value("${datasource.username}")
	private String username = "admincdb";
	//@Value("${datasource.password}")
	private String password;
	//@Value("${datasource.driver}")
	private String driver = "com.mysql.jdbc.Driver";
	
	

	/*
	 * @Bean public static PropertySourcesPlaceholderConfigurer
	 * propertySourcesPlaceholderConfigurer() { return new
	 * PropertySourcesPlaceholderConfigurer(); }
	 */
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
