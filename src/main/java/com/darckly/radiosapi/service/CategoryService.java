package com.darckly.radiosapi.service;

import com.darckly.radiosapi.dto.CategoryCreateDTO;
import com.darckly.radiosapi.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
  CategoryDTO createCategory(CategoryCreateDTO createDTO);

  CategoryDTO getCategoryById(Long id);

  List<CategoryDTO> getAllCategories();

  CategoryDTO updateCategory(Long id, CategoryCreateDTO updateDTO);

  void deleteCategory(Long id);
}
