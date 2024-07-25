package authserver.filters;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import authserver.exceptions.NoBasicAuthenticationException;
import authserver.services.KeysProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends OncePerRequestFilter {

	private KeysProvider keyProvider;
	
	private AuthenticationManager authManager;
	
	public LoginFilter(AuthenticationManager authManager, KeysProvider keyProvider) {
		this.authManager = authManager;
		System.out.println(this.authManager);
		this.keyProvider = keyProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Get user-name and password from the request header.
		// Validate the identity
		// if valid:
		//		create token, and sign it with private key.
	    //      put the access token in response header and send it back to client.
		// else:
		//		just send back 401 status code.
		
		try {
			Map<String, String> usernameAndPassword = getUsernameAndPassword(request);
			Authentication auth = validateIdentity(usernameAndPassword.get("username"), usernameAndPassword.get("password"));
			
			if(auth == null) {
				response.sendError(401, "!!! BAD CREDENTIALS !!!");
				System.out.println("!!! Auth is null !!!");
				return;
			}
			
			String token = createToken(auth);
			response.setHeader("Authentication", "Bearer " + token);
			return;
			
		} catch (NoBasicAuthenticationException | InvalidKeyException | NoSuchAlgorithmException e) {
			response.sendError(400, "!!! Something Went Wrong !!!");
			System.out.println("Something Went Wrong");
		}
	}

	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		System.out.println(request.getServletPath());
		return !request.getServletPath()
				       .matches("/login");
	}
	
	private Map<String,String> getUsernameAndPassword(HttpServletRequest request) throws NoBasicAuthenticationException {
		
		String basicHeader = request.getHeader("Authorization");
		
		if(!basicHeader.startsWith("Basic")) throw new NoBasicAuthenticationException("Wrong Authentication Header");
		
		basicHeader = basicHeader.substring(6);
		
		System.out.println("Basic encoded header : " + basicHeader);
		
		basicHeader = new String(Base64.getDecoder().decode(basicHeader));
		
		System.out.println("Decoded authentication header: " + basicHeader);
		
		String username = basicHeader.substring(0, basicHeader.indexOf(':'));
		String password = basicHeader.substring(basicHeader.indexOf(':')+1);
		
		System.out.println("Username : " + username);
		System.out.println("Password : " + password);
		
		return Map.of("username",username,"password", password);
	}
	
	private Authentication validateIdentity(String username, String password) {
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			auth = authManager.authenticate(auth);
			
			System.out.println("Principal : " + auth.getName());
			System.out.println("Authorities : " + auth.getAuthorities());
			
		} catch (BadCredentialsException ex) {
			System.out.println("!!! Bad Credentials !!!");
			throw ex;
		}
		
		if(auth.isAuthenticated()) return auth;
		else return null;
	}
	
	private String createToken(Authentication auth) throws InvalidKeyException, NoSuchAlgorithmException {
		
		Date date = new Date();
		
		System.out.println();
		String jwtToken =  Jwts.builder()
				   			   .issuer(auth.getName())
				   			   .issuedAt(date)
				   			   .expiration(new Date(date.getTime()+(24*60*60*1000)))
				   			   .claims()
				   			   .add("authorities", auth.getAuthorities().toString())
				   			   .and()
				   			   .signWith(keyProvider.getPrivateKey())
				   			   .compact()
				   			   ;
		
		System.out.println("Token created : " + jwtToken);
		return jwtToken;
	}
}