package com.darckly.radiosapi.category.dto;

import lombok.Data;

@Data
public class CategoryResponseDTO {
  private Long id;
  private String name;

  public CategoryResponseDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

}
