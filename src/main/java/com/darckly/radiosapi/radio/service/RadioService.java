package com.darckly.radiosapi.radio.service;

import java.util.List;

import com.darckly.radiosapi.radio.dto.RadioCreateDTO;
import com.darckly.radiosapi.radio.dto.RadioDTO;

public interface RadioService {
  List<RadioDTO> getAll();

  RadioDTO getById(Long id);

  RadioDTO create(RadioCreateDTO createDTO);

  RadioDTO update(Long id, RadioCreateDTO updateDTO);

  void delete(Long id);
}
