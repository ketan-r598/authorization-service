package authserver.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import authserver.services.UserAuthenticationDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/register")
													 .permitAll()
													 .requestMatchers("/login")
													 .permitAll()
													 .anyRequest()
													 .authenticated()
								  );
		
		http.cors(cors -> cors.disable());
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserAuthenticationDetailsService();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
