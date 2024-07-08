package authserver.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import authserver.services.AuditAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JPAConfiguration {

	// If we have multiple auditorAware beans in the application context.
	// then, auditorAwareRef attribute of @EnableJpaAuditing can be used to specify
	// the bean which we want to use.
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditAwareImpl();
	}
}
