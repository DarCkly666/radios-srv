package com.darckly.radiosapi.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darckly.radiosapi.category.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
