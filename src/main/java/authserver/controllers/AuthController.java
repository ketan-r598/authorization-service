package authserver.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import authserver.dtos.UserRegisterRequestDTO;
import authserver.exceptions.BadInfoException;
import authserver.exceptions.UserAlreadyExistException;
import authserver.models.User;
import authserver.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
		
		User u = new User();
		
		BeanUtils.copyProperties(userRegisterRequestDTO, u);
		
		u.setPassword(encoder.encode(userRegisterRequestDTO.getPassword()));
		
		ResponseEntity<?> response = null;
		
		try {
			User savedUser = authService.registerUser(u);
			response = new ResponseEntity<>(savedUser,HttpStatus.CREATED);
		} catch(UserAlreadyExistException ex) {
			response = new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
		} catch(BadInfoException ex) {
			response = new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			response = new ResponseEntity<>("!!! Something Went Wrong !!!", HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
