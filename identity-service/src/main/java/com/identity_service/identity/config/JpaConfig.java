package com.identity_service.identity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }


    public static class AuditorAwareImpl implements AuditorAware<String> {


        @Override
        public Optional<String> getCurrentAuditor() {
            boolean check = SecurityContextHolder.getContext().getAuthentication() instanceof  JwtAuthenticationToken;
            if(!check){
                return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return Optional.ofNullable(authentication.getName());

        }

    }
}
