package authserver.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import authserver.filters.LoginFilter;
import authserver.services.KeysProvider;
import authserver.services.UserAuthenticationDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	@Autowired
	private KeysProvider keyProvider;
	

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authManager) {
        return new LoginFilter(authManager, keyProvider);
    }
	
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
		
//		AuthenticationManager authManager = http.getSharedObject(AuthenticationManagerBuilder.class)
//				                                .getOrBuild();
		http.addFilterBefore(loginFilter(authManager(http)), BasicAuthenticationFilter.class);
		
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
	
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(encoder());
        return authManagerBuilder.build();
    }
}
