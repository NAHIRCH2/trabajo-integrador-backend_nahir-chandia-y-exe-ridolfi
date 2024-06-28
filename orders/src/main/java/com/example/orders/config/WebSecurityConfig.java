package com.example.orders.config;

import com.example.orders.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {


  @Autowired
  private JwtRequestFilter jwtRequestFilter;
  @Bean
  SecurityFilterChain web(HttpSecurity http) throws Exception {
    http
            .csrf(csrf ->csrf.disable()) // (2)
            .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger2.yaml").permitAll()
                    .requestMatchers("/publico/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE,"/api/**").permitAll()
                    .requestMatchers(HttpMethod.PUT,"/api/**").permitAll()
                    .anyRequest().authenticated()
        );

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration
      authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
