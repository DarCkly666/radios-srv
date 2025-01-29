package com.darckly.radiosapi.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.darckly.radiosapi.category.dto.CategoryCreateDTO;
import com.darckly.radiosapi.category.dto.CategoryDTO;
import com.darckly.radiosapi.category.model.Category;
import com.darckly.radiosapi.category.repository.CategoryRepository;
import com.darckly.radiosapi.exception.ResourceNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;

  public CategoryServiceImpl(CategoryRepository repository) {
    this.repository = repository;
  }

  @Override
  public CategoryDTO createCategory(CategoryCreateDTO createDTO) {
    Category category = new Category();
    category.setName(createDTO.getName());
    Category saved = repository.save(category);
    return mapToDTO(saved);
  }

  @Override
  public CategoryDTO getCategoryById(Long id) {
    Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    return mapToDTO(category);
  }

  @Override
  public List<CategoryDTO> getAllCategories() {
    List<CategoryDTO> categories = repository.findAll().stream()
        .map((Category category) -> mapToDTO(category))
        .collect(Collectors.toList());
    return categories;
  }

  @Override
  public CategoryDTO updateCategory(Long id, CategoryCreateDTO updateDTO) {
    Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    category.setName(updateDTO.getName());
    Category updated = repository.save(category);
    return mapToDTO(updated);
  }

  @Override
  public void deleteCategory(Long id) {
    Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    repository.delete(category);
  }

  private CategoryDTO mapToDTO(Category category) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setId(category.getId());
    categoryDTO.setName(category.getName());
    categoryDTO.setCreatedAt(category.getCreatedAt());
    categoryDTO.setUpdatedAt(category.getUpdatedAt());
    return categoryDTO;
  }

}
