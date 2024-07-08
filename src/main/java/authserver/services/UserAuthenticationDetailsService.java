package authserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import authserver.models.UserAuthenticationDetails;
import authserver.repositories.UserRepository;

public class UserAuthenticationDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.findByUsername(username)
				       .map(UserAuthenticationDetails::new)
				       .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " does not exist."));
	}
}
