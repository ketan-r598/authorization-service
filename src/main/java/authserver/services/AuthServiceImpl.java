package authserver.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import authserver.exceptions.BadInfoException;
import authserver.exceptions.UserAlreadyExistException;
import authserver.models.User;
import authserver.repositories.UserRepository;

@Component
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User registerUser(User user) throws UserAlreadyExistException, BadInfoException {

		Optional<User> u = userRepo.findByUsername(user.getUsername());
		
		if(u.isPresent()) {
			throw new UserAlreadyExistException("!!! User Already Exists !!!");
		}
		
		try {
			u = Optional.of(userRepo.save(user));
		} catch(IllegalArgumentException ex) {
			throw new BadInfoException("!!! Some fields have invalid information !!!");
		}
		
		return u.get();
	}

}
