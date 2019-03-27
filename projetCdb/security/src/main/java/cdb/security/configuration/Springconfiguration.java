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
		System.out.println(" Chargement de la Configuration de sécurité ");
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(BCryptManagerUtil.passwordencoder());
		super.configure(auth);
	}
 
	/**
	 * définie les pages à sécuriser 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/").authenticated()
			.anyRequest().denyAll().and().formLogin();
		/*
			//.and().formLogin().loginPage("/users/connexion").successForwardUrl("/users/connexion-succes").permitAll()
			.and().formLogin().loginPage("/users/connexion").successHandler(successHandler).permitAll()
			.and().logout().logoutUrl("/users/logout");
			//.and().logout().logoutSuccessUrl("/users/logout");
			 * */
			 
	}

	


}
