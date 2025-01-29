package com.darckly.radiosapi.country.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CountryDTO {
  private Long id;
  private String code;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
