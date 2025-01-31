package com.darckly.radiosapi.radio.dto;

import lombok.Data;

@Data
public class RadioCreateDTO {

  private String name;

  private String description;

  private String url;

  private Long imageId;

  private Long categoryId;

  private Long countryId;

}
