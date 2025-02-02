package com.darckly.radiosapi.user.dto;

import com.darckly.radiosapi.user.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateDTO {

  private String username;
  private String password;
  private Role role;
}
