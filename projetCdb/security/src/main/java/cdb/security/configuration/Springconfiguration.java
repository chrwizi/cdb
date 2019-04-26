package cdb.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cdb.security.jwt.JwtAuthenticationEntryPoint;
import cdb.security.jwt.JwtAuthenticationFilter;
import cdb.security.service.BCryptManagerUtil;
import cdb.security.service.ICustomUserDetailsService;

@Configuration
@ComponentScan(basePackages = "{cdb.persistence,cdb.security}")
@EnableWebSecurity
public class Springconfiguration extends WebSecurityConfigurerAdapter {

	private ICustomUserDetailsService userDetailsService;
	private JwtAuthenticationFilter authenticationFilter;
	private JwtAuthenticationEntryPoint unautAuthenticationEntryPoint;

	private final String[] REQUIRED_AUTHENTICATIION_URLS = { "/api/computers**", "/api/companies/cred" };
	private final String[] PERMIT_URL = { "/api/users/auth" };

	Logger logger = LoggerFactory.getLogger(getClass());

	public Springconfiguration(ICustomUserDetailsService userDetailsService,
			JwtAuthenticationFilter authenticationFilter, JwtAuthenticationEntryPoint unautAuthenticationEntryPoint) {
		this.userDetailsService = userDetailsService;
		this.authenticationFilter = authenticationFilter;
		this.unautAuthenticationEntryPoint = unautAuthenticationEntryPoint;
		logger.info("cdb security config loading");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(BCryptManagerUtil.passwordencoder());
		logger.info("cdb security passwordencoder setting done");
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().maximumSessions(5);
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(this.unautAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers(PERMIT_URL).permitAll().antMatchers(REQUIRED_AUTHENTICATIION_URLS)
				.authenticated();

		http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		logger.info("cdb security Autorization configuration loaded");
	}

}
