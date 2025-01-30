package com.darckly.radiosapi.radio.dto;

import java.time.LocalDateTime;

import com.darckly.radiosapi.category.dto.CategoryResponseDTO;
import com.darckly.radiosapi.country.dto.CountryResponseDTO;

import lombok.Data;

@Data
public class RadioDTO {

  private Long id;

  private String name;

  private String description;

  private String url;

  private String imageLarge;

  private String imageThumbnail;

  private CategoryResponseDTO category;

  private CountryResponseDTO country;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
