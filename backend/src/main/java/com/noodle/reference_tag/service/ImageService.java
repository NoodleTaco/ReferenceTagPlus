package com.noodle.reference_tag.service;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.entity.ImageEntity;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageDto save(ImageDto imageDto);

    Optional<ImageDto> findImageById(Long id);

    List<ImageDto> findAllImages();

    boolean isExists(Long id);

    void deleteImageById(Long id);

    Optional<ImageDto> findByPath(String path);
}
