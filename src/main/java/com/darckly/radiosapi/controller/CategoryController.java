package com.darckly.radiosapi.controller;

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

import com.darckly.radiosapi.dto.CategoryCreateDTO;
import com.darckly.radiosapi.dto.CategoryDTO;
import com.darckly.radiosapi.service.CategoryService;

@RestController
@RequestMapping(path = "/api/v1/category")
public class CategoryController {
  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    List<CategoryDTO> categories = service.getAllCategories();
    return ResponseEntity.ok(categories);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") Long id) {
    var category = service.getCategoryById(id);
    return ResponseEntity.ok(category);
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryCreateDTO createDTO) {
    var created = service.createCategory(createDTO);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(@PathVariable(name = "id") Long id,
      @RequestBody CategoryCreateDTO updateDTO) {
    var updated = service.updateCategory(id, updateDTO);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
    service.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
