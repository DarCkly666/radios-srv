package com.darckly.radiosapi.radio.controller;

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

import com.darckly.radiosapi.radio.dto.RadioCreateDTO;
import com.darckly.radiosapi.radio.dto.RadioDTO;
import com.darckly.radiosapi.radio.service.RadioService;

@RestController
@RequestMapping(path = "/api/v1/radio")
public class RadioController {

  private final RadioService service;

  public RadioController(RadioService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<RadioDTO>> getAll() {
    var radios = service.getAll();
    return ResponseEntity.ok(radios);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<RadioDTO> getById(@PathVariable(name = "id") Long id) {
    var radio = service.getById(id);
    return ResponseEntity.ok(radio);
  }

  @PostMapping
  public ResponseEntity<RadioDTO> create(@RequestBody RadioCreateDTO createDTO) {
    var created = service.create(createDTO);
    // return ResponseEntity.created(null).body(created);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<RadioDTO> update(@PathVariable(name = "id") Long id, @RequestBody RadioCreateDTO updateDTO) {
    var updated = service.update(id, updateDTO);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
