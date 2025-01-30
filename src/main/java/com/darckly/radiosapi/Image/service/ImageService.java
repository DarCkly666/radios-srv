package com.darckly.radiosapi.Image.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.darckly.radiosapi.Image.dto.ImageDTO;

public interface ImageService {

  List<ImageDTO> getAll();

  ImageDTO getById(Long id);

  ImageDTO create(MultipartFile file);

  void delete(Long id);
}
