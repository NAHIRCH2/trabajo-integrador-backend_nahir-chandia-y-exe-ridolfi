package com.example.orders.config;

import com.example.orders.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

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
                    .requestMatchers(HttpMethod.POST,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.PUT,"/api/**").authenticated()
                    .anyRequest().authenticated()
            )
            .cors(withDefaults())
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
