package cdb.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import cdb.security.service.ICustomUserDetailsService;

@Configuration
public class Springconfiguration extends WebSecurityConfigurerAdapter{
	
	
	private ICustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(this.userDetailsService).
		super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	}
	

	


}
