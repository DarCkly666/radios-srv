package com.darckly.radiosapi.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darckly.radiosapi.user.dto.UserCreateDTO;
import com.darckly.radiosapi.user.dto.UserDTO;
import com.darckly.radiosapi.user.model.User;
import com.darckly.radiosapi.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

  private final UserRepository repository;
  private final PasswordEncoder encoder;

  public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found."));
  }

  public UserDTO create(UserCreateDTO createDTO) {
    User user = new User();
    user.setUsername(createDTO.getUsername());
    user.setPassword(encoder.encode(createDTO.getPassword()));
    user.setRole(createDTO.getRole());
    User created = repository.save(user);
    return mapToDTO(created);
  }

  private UserDTO mapToDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setUsername(user.getUsername());
    userDTO.setRole(user.getRole());
    return userDTO;
  }

}
