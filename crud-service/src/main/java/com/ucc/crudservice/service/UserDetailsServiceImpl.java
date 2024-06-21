package com.ucc.crudservice.service;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var usuario = getById(username);

    if (usuario == null) {
      throw new UsernameNotFoundException(username);
    }
    return User
        .withUsername(username)
        .password(usuario.password())
        .roles(usuario.roles().toArray(new String[0]))
        .build();
  }

  public record Usuario(String username, String password, Set<String> roles) {};

  public static Usuario getById(String username) {
    // "1234" => [BCrypt] => "$2a$12$fmtI5P9M4f0rYkSWOWXKHOqCf1VlZMYCKMZHN7G3T5cPqAXs7SiXe"
    var password = "$2a$12$fmtI5P9M4f0rYkSWOWXKHOqCf1VlZMYCKMZHN7G3T5cPqAXs7SiXe";


    Usuario admin = new Usuario(
        "admin",
        password,
        Set.of("ADMIN")
    );
    var usuarios = List.of(admin);

    return usuarios
        .stream()
        .filter(e -> e.username().equals(username))
        .findFirst()
        .orElse(null);
  }
}
