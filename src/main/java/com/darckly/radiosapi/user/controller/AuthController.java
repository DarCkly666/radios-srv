package com.darckly.radiosapi.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darckly.radiosapi.jwtUtils.JwtService;
import com.darckly.radiosapi.user.dto.UserCreateDTO;
import com.darckly.radiosapi.user.dto.UserDTO;
import com.darckly.radiosapi.user.dto.UserResponseDTO;
import com.darckly.radiosapi.user.model.Role;
import com.darckly.radiosapi.user.model.User;
import com.darckly.radiosapi.user.service.AuthServiceImpl;
import com.darckly.radiosapi.user.service.UserServiceImpl;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserServiceImpl userService;
  private final AuthServiceImpl service;

  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
      UserServiceImpl userService, AuthServiceImpl service) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userService = userService;
    this.service = service;
  }

  @PostMapping("/login")
  public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.username(), request.password()));

    User userDetails = userService.loadUserByUsername(request.username());
    String token = jwtService.generateToken(userDetails);
    UserResponseDTO response = new UserResponseDTO();
    response.setId(userDetails.getId());
    response.setUsername(userDetails.getUsername());
    response.setRole(userDetails.getRole());
    response.setToken(token);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(@RequestBody UserCreateDTO createDTO) {
    var created = service.create(createDTO);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }
}

record LoginRequest(String username, String password) {
}

record RegisterRequest(String username, String password, Role role) {
}