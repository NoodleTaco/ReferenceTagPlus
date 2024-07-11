package com.noodle.reference_tag.service;

import com.noodle.reference_tag.domain.ImageEntity;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageEntity save(ImageEntity imageEntity);

    Optional<ImageEntity> findImageById(Long id);

    List<ImageEntity> findAllImages();

    boolean isExists(Long id);

    void deleteImageById(Long id);

    Optional<ImageEntity> findByPath(String path);
}
