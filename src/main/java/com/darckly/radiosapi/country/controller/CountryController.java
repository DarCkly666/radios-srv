package com.darckly.radiosapi.country.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darckly.radiosapi.country.dto.CountryCreateDTO;
import com.darckly.radiosapi.country.dto.CountryDTO;
import com.darckly.radiosapi.country.service.CountryService;

@RestController
@RequestMapping(path = "/api/v1/country")
public class CountryController {

  private final CountryService service;

  public CountryController(CountryService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<CountryDTO>> getAll() {
    var countries = service.getAll();
    return ResponseEntity.ok(countries);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<CountryDTO> getById(@PathVariable(name = "id") Long id) {
    var country = service.getById(id);
    return ResponseEntity.ok(country);
  }

  @PostMapping
  public ResponseEntity<CountryDTO> create(@RequestBody CountryCreateDTO createDTO) {
    var created = service.create(createDTO);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<CountryDTO> update(@PathVariable(name = "id") Long id,
      @RequestBody CountryCreateDTO updateDTO) {
    var updated = service.update(id, updateDTO);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
