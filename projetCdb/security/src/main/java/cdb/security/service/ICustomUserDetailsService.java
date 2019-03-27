package cdb.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICustomUserDetailsService extends UserDetailsService{
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException ;
}
