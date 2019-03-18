package app.projetCdb.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "datasource")
@PropertySource("classpath:application.properties")
public class CdbDataSourceProperties{
	

	@Value("${datasource.url}")
	private String url;
			;
	@Value("${datasource.username}")
	private String username;
	//private String username="admincdb";
	@Value("${datasource.password}")
	private String password;
  
	
	public CdbDataSourceProperties() {
		
		
		System.out.println("\n\n");
		System.out.println(url);
		System.out.println(username);
		System.out.println(password);
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
