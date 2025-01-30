package com.darckly.radiosapi.radio.dto;

import lombok.Data;

@Data
public class RadioCreateDTO {

  private String name;

  private String description;

  private String url;

  private String imageLarge;

  private String imageThumbnail;

  private Long categoryId;

  private Long countryId;

}
