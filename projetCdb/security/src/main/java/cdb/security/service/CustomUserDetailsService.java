package cdb.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService  implements ICustomUserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	/*	
		Optional<User> optionalUser=userManager.findByEmail(username);
		if(optionalUser.isPresent()) {
			return new CustomUserDetails(optionalUser.get());
		}
		System.out.println("\n\n>>>>>>>> user mail  not found  >>>>>>\n\n\n");
		*/
		return null;

	}

}
