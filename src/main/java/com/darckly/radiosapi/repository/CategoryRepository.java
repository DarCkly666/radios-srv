package com.darckly.radiosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darckly.radiosapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
