package com.noodle.reference_tag.service.impl;

import com.noodle.reference_tag.domain.ImageEntity;
import com.noodle.reference_tag.domain.ImageTagEntity;
import com.noodle.reference_tag.domain.TagEntity;
import com.noodle.reference_tag.repository.ImageRepository;
import com.noodle.reference_tag.repository.ImageTagRepository;
import com.noodle.reference_tag.repository.TagRepository;
import com.noodle.reference_tag.service.ImageTagService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageTagServiceImpl implements ImageTagService {


    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final ImageTagRepository imageTagRepository;

    public ImageTagServiceImpl(ImageRepository imageRepository, TagRepository tagRepository, ImageTagRepository imageTagRepository) {
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.imageTagRepository = imageTagRepository;
    }


    /**
     * Attaches a tag to an image by creating an ImageTag entity
     * @param imageId The id of the image
     * @param tagId The id of the tag
     * @return A copy of the ImageTagEntity that was saved to the db
     */
    @Override
    @Transactional
    public ImageTagEntity associateTagWithImage(Long imageId, Long tagId) {
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

        return imageTagRepository.save(imageTag);
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
    public List<TagEntity> getTagsForImage(Long imageId) {
        ImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        List<ImageTagEntity> imageTags = imageTagRepository.findByImageId(image.getId());

        return imageTags.stream()
                .map(ImageTagEntity::getTag)
                .toList();

    }

    @Override
    @Transactional
    public List<ImageTagEntity> findByImageIdAndTagId(Long imageId, Long tagId) {
        return imageTagRepository.findByImage_IdAndTag_Id(imageId, tagId);
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
    public List<ImageEntity> findImageBySearchedTags(List<Long> tagIds) {
        if (tagIds.isEmpty()) {
            return imageRepository.findAll();
        }
        return imageTagRepository.findImagesByAllTags(tagIds, tagIds.size());
    }
}
