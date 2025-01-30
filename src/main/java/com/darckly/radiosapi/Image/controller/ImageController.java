package com.darckly.radiosapi.Image.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.darckly.radiosapi.Image.dto.ImageDTO;
import com.darckly.radiosapi.Image.service.ImageService;

@RestController
@RequestMapping(path = "/api/v1/image")
public class ImageController {

  private final ImageService service;

  public ImageController(ImageService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ImageDTO>> getAll() {
    List<ImageDTO> images = service.getAll();
    return ResponseEntity.ok(images);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<ImageDTO> getById(@PathVariable(name = "id") Long id) {
    ImageDTO image = service.getById(id);
    return ResponseEntity.ok(image);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ImageDTO> create(@RequestPart("image") MultipartFile image) {
    ImageDTO created = service.create(image);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
