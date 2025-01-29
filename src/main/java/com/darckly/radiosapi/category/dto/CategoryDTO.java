package com.darckly.radiosapi.category.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CategoryDTO {
  private Long id;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
