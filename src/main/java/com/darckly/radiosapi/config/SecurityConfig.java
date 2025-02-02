package com.darckly.radiosapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth

            // pulic access (no authentication)
            .requestMatchers(HttpMethod.GET, "/api/v1/radio/**").permitAll()

            // role USER access
            .requestMatchers(HttpMethod.GET, "/api/v1/category/**").hasAnyRole("USER", "EDITOR", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/v1/country/**").hasAnyRole("USER", "EDITOR", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/v1/image/**").hasAnyRole("USER", "EDITOR", "ADMIN")

            // role EDITOR access
            .requestMatchers("/api/v1/category/**").hasAnyRole("EDITOR", "ADMIN")
            .requestMatchers("/api/v1/country/**").hasAnyRole("EDITOR", "ADMIN")
            .requestMatchers("/api/v1/image/**").hasAnyRole("EDITOR", "ADMIN")
            .requestMatchers("/api/v1/radio/**").hasAnyRole("EDITOR", "ADMIN")
            .requestMatchers("/api/v1/user/register").hasAnyRole("EDITOR", "ADMIN")

            // role ADMIN access
            .requestMatchers("/api/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .httpBasic(withDefaults());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
