package cdb.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import cdb.security.service.BCryptManagerUtil;
import cdb.security.service.ICustomUserDetailsService;

@Configuration
@ComponentScan(basePackages="{cdb.persistence,cdb.security}")
@EnableWebSecurity
public class Springconfiguration extends WebSecurityConfigurerAdapter{
	Logger logger=LoggerFactory.getLogger(getClass());
	private ICustomUserDetailsService userDetailsService;

	public Springconfiguration(ICustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		logger.info("cdb security config loading");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(BCryptManagerUtil.passwordencoder());
		logger.info("cdb security passwordencoder setting done");
	}
 

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().maximumSessions(1);
		http.csrf().disable();
		//http.authorizeRequests().anyRequest().permitAll().and().formLogin();		
		logger.info("cdb security Autorization configuration loaded");
	}
}
