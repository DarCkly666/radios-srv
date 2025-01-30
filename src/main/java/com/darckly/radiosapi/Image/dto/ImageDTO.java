package com.darckly.radiosapi.Image.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageDTO {
  private Long id;

  private String originalPath;

  private String thumbnailPath;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
