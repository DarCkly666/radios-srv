package com.darckly.radiosapi.country.dto;

import lombok.Data;

@Data
public class CountryResponseDTO {
  private Long id;
  private String code;
  private String name;

  public CountryResponseDTO(Long id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

}
