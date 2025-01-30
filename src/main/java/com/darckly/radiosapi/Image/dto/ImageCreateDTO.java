package com.darckly.radiosapi.Image.dto;

import lombok.Data;

@Data
public class ImageCreateDTO {
  private Long id;

  private String originalPath;

  private String thumbnailPath;

  public ImageCreateDTO(Long id, String originalPath, String thumbnailPath) {
    this.id = id;
    this.originalPath = originalPath;
    this.thumbnailPath = thumbnailPath;
  }

}
