package com.example.demo;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableJpaAuditing
public class TestApplication {

	@Bean
    public AuditorAware<String> auditorAware()
    {
        return new SecurityAuditorAware();
    }

    public static class SecurityAuditorAware implements AuditorAware<String>
    {
        @Override
        public Optional<String> getCurrentAuditor()
        {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated())
            {
                return null;
            }
            String userName = authentication.getName();

            return Optional.ofNullable(userName);
        }
    }
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
