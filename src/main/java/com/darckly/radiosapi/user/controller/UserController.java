package com.darckly.radiosapi.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darckly.radiosapi.user.dto.UserCreateDTO;
import com.darckly.radiosapi.user.dto.UserDTO;
import com.darckly.radiosapi.user.service.UserServiceImpl;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

  private final UserServiceImpl service;

  public UserController(UserServiceImpl service) {
    this.service = service;
  }

  @PostMapping(path = "/register")
  public ResponseEntity<UserDTO> register(@RequestBody UserCreateDTO createDTO) {
    System.out.println(createDTO.getUsername());
    UserDTO created = service.create(createDTO);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

}
