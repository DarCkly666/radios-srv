package com.darckly.radiosapi.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darckly.radiosapi.user.dto.UserDTO;
import com.darckly.radiosapi.user.service.UserServiceImpl;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

  private final UserServiceImpl service;

  public UserController(UserServiceImpl service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAll() {
    var users = service.getAll();
    return ResponseEntity.ok(users);
  }

}
