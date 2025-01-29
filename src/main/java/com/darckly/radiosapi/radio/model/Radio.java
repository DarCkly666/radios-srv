package com.darckly.radiosapi.radio.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.darckly.radiosapi.category.model.Category;
import com.darckly.radiosapi.country.model.Country;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Radio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "description", nullable = true, length = 250)
  private String description;

  @Column(name = "url", nullable = false, length = 250)
  private String url;

  @Column(name = "image_large", nullable = false, length = 250)
  private String imageLarge;

  @Column(name = "image_thumbnail", nullable = false, length = 250)
  private String imageThumbnail;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @ManyToOne
  @JoinColumn(name = "country_id", nullable = false)
  private Country country;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;
}
