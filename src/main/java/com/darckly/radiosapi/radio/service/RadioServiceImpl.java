package com.darckly.radiosapi.radio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.darckly.radiosapi.Image.model.Image;
import com.darckly.radiosapi.Image.repository.ImageRepository;
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
public class RadioServiceImpl implements RadioService {

  private final RadioRepository repository;
  private final CountryRepository countryRepository;
  private final CategoryRepository categoryRepository;
  private final ImageRepository imageRepository;

  public RadioServiceImpl(RadioRepository repository, CountryRepository countryRepository,
      CategoryRepository categoryRepository, ImageRepository imageRepository) {
    this.repository = repository;
    this.countryRepository = countryRepository;
    this.categoryRepository = categoryRepository;
    this.imageRepository = imageRepository;
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

  @Transactional
  @Override
  public RadioDTO create(RadioCreateDTO createDTO) {
    Country country = countryRepository.findById(createDTO.getCountryId())
        .orElseThrow(() -> new ResourceNotFoundException("Country not found."));

    Category category = categoryRepository.findById(createDTO.getCategoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

    Image image = imageRepository.findById(createDTO.getImageId())
        .orElseThrow(() -> new ResourceNotFoundException("Image not found."));

    Radio radio = new Radio();
    radio.setName(createDTO.getName());
    radio.setDescription(createDTO.getDescription());
    radio.setUrl(createDTO.getUrl());
    radio.setImage(image);
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

    Image image = imageRepository.findById(updateDTO.getImageId())
        .orElseThrow(() -> new ResourceNotFoundException("Image not found."));

    radio.setName(updateDTO.getName());
    radio.setDescription(updateDTO.getDescription());
    radio.setUrl(updateDTO.getUrl());
    radio.setImage(image);
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
    radioDTO.setImageLarge(radio.getImage().getOriginalPath());
    radioDTO.setImageThumbnail(radio.getImage().getThumbnailPath());
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
