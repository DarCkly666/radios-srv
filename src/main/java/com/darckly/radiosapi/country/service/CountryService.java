package com.darckly.radiosapi.country.service;

import java.util.List;

import com.darckly.radiosapi.country.dto.CountryCreateDTO;
import com.darckly.radiosapi.country.dto.CountryDTO;

public interface CountryService {
  List<CountryDTO> getAll();

  CountryDTO getById(Long id);

  CountryDTO create(CountryCreateDTO createDTO);

  CountryDTO update(Long id, CountryCreateDTO updateDTO);

  void delete(Long id);
}
