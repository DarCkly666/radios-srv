package com.darckly.radiosapi.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.darckly.radiosapi.user.dto.UserDTO;
import com.darckly.radiosapi.user.model.User;
import com.darckly.radiosapi.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found."));
  }

  public List<UserDTO> getAll() {
    List<UserDTO> users = repository.findAll().stream()
        .map((user) -> new UserDTO(user.getId(), user.getUsername(), user.getRole())).toList();
    return users;
  }
}
