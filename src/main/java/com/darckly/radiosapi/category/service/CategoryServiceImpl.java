package com.darckly.radiosapi.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.darckly.radiosapi.category.dto.CategoryCreateDTO;
import com.darckly.radiosapi.category.dto.CategoryDTO;
import com.darckly.radiosapi.category.model.Category;
import com.darckly.radiosapi.category.repository.CategoryRepository;
import com.darckly.radiosapi.exception.ConflictException;
import com.darckly.radiosapi.exception.ResourceNotFoundException;
import com.darckly.radiosapi.radio.repository.RadioRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;
  private final RadioRepository radioRepository;

  public CategoryServiceImpl(CategoryRepository repository, RadioRepository radioRepository) {
    this.repository = repository;
    this.radioRepository = radioRepository;
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
    if (radioRepository.existsByCategoryId(id)) {
      throw new ConflictException("Can not delete category because it has some references.");
    }
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
