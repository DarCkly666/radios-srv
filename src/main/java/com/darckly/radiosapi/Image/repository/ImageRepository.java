package com.darckly.radiosapi.Image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darckly.radiosapi.Image.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
