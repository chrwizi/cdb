package cdb.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cdb.security.service.BCryptManagerUtil;
import cdb.security.service.ICustomUserDetailsService;
import jwt.JwtAuthenticationFilter;

@Configuration
@ComponentScan(basePackages = "{cdb.persistence,cdb.security}")
@EnableWebSecurity
public class Springconfiguration extends WebSecurityConfigurerAdapter {

	private ICustomUserDetailsService userDetailsService;
	private JwtAuthenticationFilter authenticationFilter;

	Logger logger = LoggerFactory.getLogger(getClass());

	public Springconfiguration(ICustomUserDetailsService userDetailsService,
			JwtAuthenticationFilter authenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.authenticationFilter = authenticationFilter;
	}

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
		http.sessionManagement().maximumSessions(5);
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/editComputer/**").authenticated();
		http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		logger.info("cdb security Autorization configuration loaded");
	}

}
