package com.noodle.reference_tag.service.impl;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.dto.ImageTagDto;
import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.domain.entity.ImageEntity;
import com.noodle.reference_tag.domain.entity.ImageTagEntity;
import com.noodle.reference_tag.domain.entity.TagEntity;
import com.noodle.reference_tag.mapper.impl.ImageMapper;
import com.noodle.reference_tag.mapper.impl.ImageTagMapper;
import com.noodle.reference_tag.mapper.impl.TagMapper;
import com.noodle.reference_tag.repository.ImageRepository;
import com.noodle.reference_tag.repository.ImageTagRepository;
import com.noodle.reference_tag.repository.TagRepository;
import com.noodle.reference_tag.service.ImageService;
import com.noodle.reference_tag.service.ImageTagService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageTagServiceImpl implements ImageTagService {


    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final ImageTagRepository imageTagRepository;

    private final ImageTagMapper imageTagMapper;

    private final TagMapper tagMapper;

    private final ImageMapper imageMapper;

    public ImageTagServiceImpl(ImageRepository imageRepository, TagRepository tagRepository, ImageTagRepository imageTagRepository, ImageTagMapper imageTagMapper, TagMapper tagMapper, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.imageTagRepository = imageTagRepository;
        this.imageTagMapper = imageTagMapper;

        this.tagMapper = tagMapper;
        this.imageMapper = imageMapper;
    }


    /**
     * Attaches a tag to an image by creating an ImageTag entity
     * @param imageId The id of the image
     * @param tagId The id of the tag
     * @return A copy of the ImageTagEntity that was saved to the db
     */
    @Override
    @Transactional
    public ImageTagDto associateTagWithImage(Long imageId, Long tagId) {
        //Find Image
        ImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        //Find Tag
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));

        ImageTagEntity imageTag = ImageTagEntity.builder()
                .image(image)
                .tag(tag)
                .build();


        ImageTagEntity savedEntity = imageTagRepository.save(imageTag);
        return imageTagMapper.mapTo(savedEntity);
    }

    /**
     * Removes a tag from an image by deleting the ImageTag associated with them
     * @param imageId The id of the image to be manipulated
     * @param tagId The id of the tag that will be removed from the image
     */
    @Override
    @Transactional
    public void removeTagFromImage(Long imageId, Long tagId) {
        ImageTagEntity imageTag = imageTagRepository.findByImageIdAndTagId(imageId, tagId)
                .orElseThrow(() -> new RuntimeException("Image-Tag association not found"));

        imageTagRepository.delete(imageTag);
    }

    /**
     *Provides the list of tags associated with a given imageId
     * @param imageId The id of the image being looked at
     * @return A List of ImageTag Entities associated with the image
     */
    @Override
    @Transactional
    public List<TagDto> getTagsForImage(Long imageId) {
        ImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        List<ImageTagEntity> imageTags = imageTagRepository.findByImageId(image.getId());

        List<TagEntity> tagEntityList = imageTags.stream()
                .map(ImageTagEntity::getTag)
                .toList();

        return tagEntityList.stream()
                .map(tagMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ImageTagDto> findByImageIdAndTagId(Long imageId, Long tagId) {

        List<ImageTagEntity> imageTagEntityList = imageTagRepository.findByImage_IdAndTag_Id(imageId, tagId);
        return imageTagEntityList.stream()
                .map(imageTagMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByImageId(Long imageId) {
        imageTagRepository.deleteByImage_Id(imageId);
    }

    @Override
    @Transactional
    public void deleteByTagId(Long tagId) {
        imageTagRepository.deleteByTag_Id(tagId);
    }

    @Override
    @Transactional
    public List<ImageDto> findImageBySearchedTags(List<Long> tagIds) {
        if (tagIds.isEmpty()) {
            return imageRepository.findAll().stream()
                    .map(imageMapper::mapTo)
                    .collect(Collectors.toList());
        }
        List<ImageEntity> imageEntityList = imageTagRepository.findImagesByAllTags(tagIds, tagIds.size());
        return imageEntityList.stream()
                .map(imageMapper::mapTo)
                .collect(Collectors.toList());
    }
}
