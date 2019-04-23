package cdb.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cdb.core.models.User;
import cdb.persistence.dao.UserDao;
import cdb.security.user.CustomUserDetails;

@Service
public class CustomUserDetailsService implements ICustomUserDetailsService {
	private UserDao userDao;

	public CustomUserDetailsService(UserDao userDao) {
		this.userDao = userDao; 
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optionalUser = userDao.findByUsername(username);
		if (optionalUser.isPresent()) {
			return new CustomUserDetails(optionalUser.get());
		} 
		return null;
	}

	public UserDetails loadUserById(Long id) {
		Optional<User> optionalUser = userDao.findById(id);
		return (optionalUser.isPresent() ? new CustomUserDetails(optionalUser.get()) : null);
	}
}
