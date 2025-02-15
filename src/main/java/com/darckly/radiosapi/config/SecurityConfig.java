package com.darckly.radiosapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.darckly.radiosapi.jwtUtils.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /*
   * @Bean
   * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
   * Exception {
   * http
   * .csrf(csrf -> csrf.disable())
   * .authorizeHttpRequests(auth -> auth
   * 
   * // pulic access (no authentication)
   * .requestMatchers(HttpMethod.GET, "/api/v1/radio/**").permitAll()
   * 
   * // role USER access
   * .requestMatchers(HttpMethod.GET, "/api/v1/category/**").hasAnyRole("USER",
   * "EDITOR", "ADMIN")
   * .requestMatchers(HttpMethod.GET, "/api/v1/country/**").hasAnyRole("USER",
   * "EDITOR", "ADMIN")
   * .requestMatchers(HttpMethod.GET, "/api/v1/image/**").hasAnyRole("USER",
   * "EDITOR", "ADMIN")
   * 
   * // role EDITOR access
   * .requestMatchers("/api/v1/category/**").hasAnyRole("EDITOR", "ADMIN")
   * .requestMatchers("/api/v1/country/**").hasAnyRole("EDITOR", "ADMIN")
   * .requestMatchers("/api/v1/image/**").hasAnyRole("EDITOR", "ADMIN")
   * .requestMatchers("/api/v1/radio/**").hasAnyRole("EDITOR", "ADMIN")
   * .requestMatchers("/api/v1/user/register").hasAnyRole("EDITOR", "ADMIN")
   * 
   * // role ADMIN access
   * .requestMatchers("/api/**").hasRole("ADMIN")
   * .anyRequest().authenticated())
   * .httpBasic(withDefaults());
   * 
   * return http.build();
   * }
   */

  private final JwtAuthFilter jwtAuthFilter;
  private final UserDetailsService userService;

  public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userService) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.userService = userService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/auth/**").permitAll()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/user/**").hasAnyRole("EDITOR", "ADMIN")
        .anyRequest().authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200");
      }
    };
  }
}
