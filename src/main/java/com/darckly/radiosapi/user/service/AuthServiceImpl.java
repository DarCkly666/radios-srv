package com.darckly.radiosapi.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darckly.radiosapi.exception.BadRequestException;
import com.darckly.radiosapi.user.dto.UserCreateDTO;
import com.darckly.radiosapi.user.dto.UserDTO;
import com.darckly.radiosapi.user.model.Role;
import com.darckly.radiosapi.user.model.User;
import com.darckly.radiosapi.user.repository.UserRepository;

@Service
public class AuthServiceImpl {

  private final UserRepository repository;
  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserRepository repository, PasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  public UserDTO create(UserCreateDTO createDTO) {
    User user = new User();
    user.setUsername(createDTO.getUsername());
    user.setPassword(encoder.encode(createDTO.getPassword()));
    if (!List.of(Role.values()).contains(createDTO.getRole())) {
      throw new BadRequestException(createDTO.getRole() + "is not a valid Role");
    }
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
