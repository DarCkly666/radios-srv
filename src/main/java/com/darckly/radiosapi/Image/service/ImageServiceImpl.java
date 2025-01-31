package com.darckly.radiosapi.Image.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.darckly.radiosapi.Image.dto.ImageDTO;
import com.darckly.radiosapi.Image.model.Image;
import com.darckly.radiosapi.Image.repository.ImageRepository;
import com.darckly.radiosapi.exception.BadRequestException;
import com.darckly.radiosapi.exception.ConflictException;
import com.darckly.radiosapi.exception.ResourceNotFoundException;
import com.darckly.radiosapi.exception.UnsupportedMediaTypeException;
import com.darckly.radiosapi.radio.repository.RadioRepository;

import jakarta.annotation.PostConstruct;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageServiceImpl implements ImageService {

  private final ImageRepository repository;
  private final RadioRepository radioRepository;
  private final Path rootLocation;

  public ImageServiceImpl(ImageRepository repository, RadioRepository radioRepository) {
    this.repository = repository;
    this.radioRepository = radioRepository;
    rootLocation = Paths.get("uploads/images");
  }

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new RuntimeException("Could not create directory");
    }
  }

  @Override
  public List<ImageDTO> getAll() {
    List<ImageDTO> images = repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    return images;
  }

  @Override
  public ImageDTO getById(Long id) {
    Image image = repository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Image not found."));
    return mapToDTO(image);
  }

  @Override
  public ImageDTO create(MultipartFile file) {

    if (file.isEmpty()) {
      throw new BadRequestException("File is empty.");
    }

    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      throw new UnsupportedMediaTypeException("File is not an image.");
    }

    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null || !isImageExtension(originalFilename)) {
      throw new BadRequestException("File is not an image.");
    }

    try {
      // Generate files name
      String originalFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
      String thumbnailFileName = "thumbnail_" + originalFileName;

      // Save image
      Path originalPath = this.rootLocation.resolve(originalFileName);
      Files.copy(file.getInputStream(), originalPath, StandardCopyOption.REPLACE_EXISTING);

      // Generate and save thumbnail
      Path thumbnailPath = this.rootLocation.resolve(thumbnailFileName);
      var imageb = ImageIO.read(file.getInputStream());
      int width = imageb.getWidth();
      int height = imageb.getHeight();
      Thumbnails.of(file.getInputStream())
          .size(width / 2, height / 2)
          .toFile(thumbnailPath.toFile());

      Image image = new Image();
      image.setOriginalPath("/uploads/images/" + originalFileName);
      image.setThumbnailPath("/uploads/images/" + thumbnailFileName);
      Image saved = repository.save(image);
      return mapToDTO(saved);

    } catch (IOException e) {
      throw new RuntimeException("Error saving image: " + e.getMessage());
    }
  }

  @Override
  public void delete(Long id) {
    Image image = repository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Image not found."));

    if (radioRepository.existsByImageId(id)) {
      throw new ConflictException("Can not delete image because it has some references.");
    }

    try {

      Path originalPath = Paths.get(image.getOriginalPath().substring(1));
      Path thumbnailPath = Paths.get(image.getThumbnailPath().substring(1));

      if (Files.exists(originalPath)) {
        Files.delete(originalPath);
      }

      if (Files.exists(thumbnailPath)) {
        Files.delete(thumbnailPath);
      }

    } catch (IOException e) {
      throw new RuntimeException("Error deleting files: " + e.getMessage());
    }
    repository.delete(image);
  }

  private boolean isImageExtension(String filename) {
    String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")
        || extension.equals("gif") || extension.equals("bmp") || extension.equals("webp");
  }

  private ImageDTO mapToDTO(Image image) {
    ImageDTO imageDTO = new ImageDTO(
        image.getId(),
        image.getOriginalPath(),
        image.getThumbnailPath(),
        image.getCreatedAt(),
        image.getUpdatedAt());
    return imageDTO;
  }

}
