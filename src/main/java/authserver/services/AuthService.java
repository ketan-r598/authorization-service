package authserver.services;

import org.springframework.stereotype.Service;

import authserver.exceptions.BadInfoException;
import authserver.exceptions.UserAlreadyExistException;
import authserver.models.User;

@Service
public interface AuthService {

	public User registerUser(User user) throws UserAlreadyExistException, BadInfoException;
}
