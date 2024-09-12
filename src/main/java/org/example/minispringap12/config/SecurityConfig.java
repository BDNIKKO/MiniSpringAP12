package org.example.minispringap12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/public/**", "/register").permitAll()  // Allow anonymous access to /register
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection using the updated method
                .headers((headers) -> headers
                        .contentSecurityPolicy((csp) -> csp.policyDirectives("default-src 'self'"))  // Updated CSP config
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)  // Updated Frame Options
                );

        return http.build();
    }
}
