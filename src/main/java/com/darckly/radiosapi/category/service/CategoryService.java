package com.darckly.radiosapi.category.service;

import java.util.List;

import com.darckly.radiosapi.category.dto.CategoryCreateDTO;
import com.darckly.radiosapi.category.dto.CategoryDTO;

public interface CategoryService {
  CategoryDTO createCategory(CategoryCreateDTO createDTO);

  CategoryDTO getCategoryById(Long id);

  List<CategoryDTO> getAllCategories();

  CategoryDTO updateCategory(Long id, CategoryCreateDTO updateDTO);

  void deleteCategory(Long id);
}
