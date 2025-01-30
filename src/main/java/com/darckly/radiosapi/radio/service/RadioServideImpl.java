package com.darckly.radiosapi.radio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.darckly.radiosapi.category.dto.CategoryResponseDTO;
import com.darckly.radiosapi.category.model.Category;
import com.darckly.radiosapi.category.repository.CategoryRepository;
import com.darckly.radiosapi.country.dto.CountryResponseDTO;
import com.darckly.radiosapi.country.model.Country;
import com.darckly.radiosapi.country.repository.CountryRepository;
import com.darckly.radiosapi.exception.ResourceNotFoundException;
import com.darckly.radiosapi.radio.dto.RadioCreateDTO;
import com.darckly.radiosapi.radio.dto.RadioDTO;
import com.darckly.radiosapi.radio.model.Radio;
import com.darckly.radiosapi.radio.repository.RadioRepository;

@Service
public class RadioServideImpl implements RadioService {

  private final RadioRepository repository;
  private final CountryRepository countryRepository;
  private final CategoryRepository categoryRepository;

  public RadioServideImpl(RadioRepository repository, CountryRepository countryRepository,
      CategoryRepository categoryRepository) {
    this.repository = repository;
    this.countryRepository = countryRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<RadioDTO> getAll() {
    List<RadioDTO> radios = repository.findAll()
        .stream().map(this::mapToDTO)
        .collect(Collectors.toList());
    return radios;
  }

  @Override
  public RadioDTO getById(Long id) {
    Radio radio = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Radio not found."));
    return mapToDTO(radio);
  }

  @Override
  public RadioDTO create(RadioCreateDTO createDTO) {
    Country country = countryRepository.findById(createDTO.getCountryId())
        .orElseThrow(() -> new ResourceNotFoundException("Country not found."));

    Category category = categoryRepository.findById(createDTO.getCategoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

    Radio radio = new Radio();
    radio.setName(createDTO.getName());
    radio.setDescription(createDTO.getDescription());
    radio.setUrl(createDTO.getUrl());
    radio.setImageLarge(createDTO.getImageLarge());
    radio.setImageThumbnail(createDTO.getImageThumbnail());
    radio.setCategory(category);
    radio.setCountry(country);

    Radio created = repository.save(radio);
    return mapToDTO(created);
  }

  @Override
  public RadioDTO update(Long id, RadioCreateDTO updateDTO) {
    Radio radio = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Radio not found"));

    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

    Country country = countryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Country not found."));

    radio.setName(updateDTO.getName());
    radio.setDescription(updateDTO.getDescription());
    radio.setUrl(updateDTO.getUrl());
    radio.setImageLarge(updateDTO.getImageLarge());
    radio.setImageThumbnail(updateDTO.getImageThumbnail());
    radio.setCategory(category);
    radio.setCountry(country);

    Radio updated = repository.save(radio);
    return mapToDTO(updated);
  }

  @Override
  public void delete(Long id) {
    Radio radio = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Radio not found"));
    repository.delete(radio);
  }

  private RadioDTO mapToDTO(Radio radio) {
    RadioDTO radioDTO = new RadioDTO();
    radioDTO.setId(radio.getId());
    radioDTO.setName(radio.getName());
    radioDTO.setDescription(radio.getDescription());
    radioDTO.setUrl(radio.getUrl());
    radioDTO.setImageLarge(radio.getImageLarge());
    radioDTO.setImageThumbnail(radio.getImageThumbnail());
    radioDTO.setCategory(
        new CategoryResponseDTO(
            radio.getCategory().getId(),
            radio.getCategory().getName()));
    radioDTO.setCountry(
        new CountryResponseDTO(
            radio.getCountry().getId(),
            radio.getCountry().getCode(),
            radio.getCountry().getName()));
    radioDTO.setCreatedAt(radio.getCreatedAt());
    radioDTO.setUpdatedAt(radio.getUpdatedAt());
    return radioDTO;
  }

}
