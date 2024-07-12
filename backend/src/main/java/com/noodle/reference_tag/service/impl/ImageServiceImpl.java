package com.noodle.reference_tag.service.impl;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.entity.ImageEntity;
import com.noodle.reference_tag.mapper.impl.ImageMapper;
import com.noodle.reference_tag.repository.ImageRepository;
import com.noodle.reference_tag.service.ImageService;
import com.noodle.reference_tag.service.ImageTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    /**
     * Holds the reference to the repository that will be injected
     */
    private final ImageRepository imageRepository;

    private final ImageTagService imageTagService;

    private final ImageMapper imageMapper;

    /**
     * Constructor that will instantiate the repository
     *
     * @param imageRepository The Image Repository that will be injected
     * @param imageTagService
     * @param imageMapper
     */
    public ImageServiceImpl(ImageRepository imageRepository, ImageTagService imageTagService, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageTagService = imageTagService;
        this.imageMapper = imageMapper;
    }


    @Override
    public ImageDto save(ImageDto imageDto) {
        ImageEntity imageEntity = imageMapper.mapFrom(imageDto);
        ImageEntity savedEntity = imageRepository.save(imageEntity);
        return imageMapper.mapTo(savedEntity);
    }

    /**
     * Attempts to return an Image correlating to a provided id, wrapping it in an Optional container
     * @param id The id to be searched for
     * @return The Optional container that holds the entity or is empty
     */
    @Override
    public Optional<ImageDto> findImageById(Long id) {
        Optional<ImageEntity> foundImage = imageRepository.findById(id);
        if(foundImage.isPresent()){
            return Optional.of(imageMapper.mapTo(foundImage.get()));
        }
        else{
            return Optional.empty();
        }
    }

    /**
     * Returns a list of all images in the DB
     * @return A list of all images in the DB
     */
    @Override
    public List<ImageDto> findAllImages() {
        return imageRepository.findAll().stream()
                .map(imageMapper::mapTo)
                .collect(Collectors.toList());
    }

    /**
     * Determines whether a given image exists in the DB
     * @param id The id of the image to be searched for
     * @return Whether the image exists
     */
    @Override
    public boolean isExists(Long id) {
        return imageRepository.existsById(id);
    }


    /**
     * Delete the provided image
     * @param id The id of the image to be deleted
     */
    @Override
    @Transactional
    public void deleteImageById(Long id) {
        //TODO: Change after ImageTag Service is Refactored
        imageTagService.deleteByImageId(id);
        imageRepository.deleteById(id);
    }

    @Override
    public Optional<ImageDto> findByPath(String path) {
        Optional<ImageEntity> foundImage = imageRepository.findByPathIgnoreCase(path);

        if(foundImage.isPresent()){
            return Optional.of(imageMapper.mapTo(foundImage.get()));
        }
        else{
            return Optional.empty();
        }
    }


}
