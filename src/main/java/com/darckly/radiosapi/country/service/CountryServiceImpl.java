package com.darckly.radiosapi.country.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.darckly.radiosapi.country.dto.CountryCreateDTO;
import com.darckly.radiosapi.country.dto.CountryDTO;
import com.darckly.radiosapi.country.model.Country;
import com.darckly.radiosapi.country.repository.CountryRepository;
import com.darckly.radiosapi.exception.ConflictException;
import com.darckly.radiosapi.exception.ResourceNotFoundException;
import com.darckly.radiosapi.radio.repository.RadioRepository;

@Service
public class CountryServiceImpl implements CountryService {

  private final CountryRepository repository;
  private final RadioRepository radioRepository;

  public CountryServiceImpl(CountryRepository repository, RadioRepository radioRepository) {
    this.repository = repository;
    this.radioRepository = radioRepository;
  }

  @Override
  public List<CountryDTO> getAll() {
    var countries = repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    return countries;
  }

  @Override
  public CountryDTO getById(Long id) {
    var country = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    return mapToDTO(country);
  }

  @Override
  public CountryDTO create(CountryCreateDTO createDTO) {
    Country country = new Country();
    country.setCode(createDTO.getCode());
    country.setName(createDTO.getName());
    var created = repository.save(country);
    return mapToDTO(created);
  }

  @Override
  public CountryDTO update(Long id, CountryCreateDTO updateDTO) {
    Country country = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    country.setCode(updateDTO.getCode());
    country.setName(updateDTO.getName());
    Country updated = repository.save(country);
    return mapToDTO(updated);
  }

  @Override
  public void delete(Long id) {
    Country country = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    if (radioRepository.existsByCountryId(id)) {
      throw new ConflictException("Can not delete country because it has some references.");
    }
    repository.delete(country);
  }

  private CountryDTO mapToDTO(Country country) {
    var countryDTO = new CountryDTO();
    countryDTO.setId(country.getId());
    countryDTO.setCode(country.getCode());
    countryDTO.setName(country.getName());
    countryDTO.setCreatedAt(country.getCreatedAt());
    countryDTO.setUpdatedAt(country.getUpdatedAt());
    return countryDTO;
  }

}
