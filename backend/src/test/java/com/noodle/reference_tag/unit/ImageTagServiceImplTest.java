package com.noodle.reference_tag.unit;

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
import com.noodle.reference_tag.service.impl.ImageTagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageTagServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ImageTagRepository imageTagRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private ImageTagMapper imageTagMapper;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ImageTagServiceImpl imageTagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssociateTagWithImage() {
        Long imageId = 1L;
        Long tagId = 1L;
        ImageEntity imageEntity = new ImageEntity();
        TagEntity tagEntity = new TagEntity();
        ImageTagEntity imageTagEntity = new ImageTagEntity();
        ImageTagDto imageTagDto = new ImageTagDto();

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(imageEntity));
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagEntity));
        when(imageTagRepository.save(any(ImageTagEntity.class))).thenReturn(imageTagEntity);
        when(imageTagMapper.mapTo(imageTagEntity)).thenReturn(imageTagDto);

        ImageTagDto result = imageTagService.associateTagWithImage(imageId, tagId);

        assertEquals(imageTagDto, result);
        verify(imageTagRepository).save(any(ImageTagEntity.class));
    }

    @Test
    void testRemoveTagFromImage() {
        Long imageId = 1L;
        Long tagId = 1L;
        ImageTagEntity imageTagEntity = new ImageTagEntity();

        when(imageTagRepository.findByImageIdAndTagId(imageId, tagId)).thenReturn(Optional.of(imageTagEntity));

        imageTagService.removeTagFromImage(imageId, tagId);

        verify(imageTagRepository).delete(imageTagEntity);
    }

    @Test
    void testGetTagsForImage() {
        Long imageId = 1L;
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(imageId);

        TagEntity tag1 = new TagEntity();
        TagEntity tag2 = new TagEntity();

        ImageTagEntity imageTag1 = new ImageTagEntity();
        imageTag1.setImage(imageEntity);
        imageTag1.setTag(tag1);

        ImageTagEntity imageTag2 = new ImageTagEntity();
        imageTag2.setImage(imageEntity);
        imageTag2.setTag(tag2);

        List<ImageTagEntity> imageTags = Arrays.asList(imageTag1, imageTag2);

        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(imageEntity));
        when(imageTagRepository.findByImageId(imageId)).thenReturn(imageTags);
        when(tagMapper.mapTo(tag1)).thenReturn(tagDto1);
        when(tagMapper.mapTo(tag2)).thenReturn(tagDto2);

        List<TagDto> result = imageTagService.getTagsForImage(imageId);

        assertEquals(2, result.size());
        assertTrue(result.contains(tagDto1));
        assertTrue(result.contains(tagDto2));
        verify(imageRepository).findById(imageId);
        verify(imageTagRepository).findByImageId(imageId);
        verify(tagMapper, times(2)).mapTo(any(TagEntity.class));
    }

    @Test
    void testFindByImageIdAndTagId() {
        Long imageId = 1L;
        Long tagId = 1L;
        List<ImageTagEntity> imageTagEntities = Arrays.asList(new ImageTagEntity(), new ImageTagEntity());
        List<ImageTagDto> imageTagDtos = Arrays.asList(new ImageTagDto(), new ImageTagDto());

        when(imageTagRepository.findByImage_IdAndTag_Id(imageId, tagId)).thenReturn(imageTagEntities);
        when(imageTagMapper.mapTo(any(ImageTagEntity.class))).thenReturn(imageTagDtos.get(0), imageTagDtos.get(1));

        List<ImageTagDto> result = imageTagService.findByImageIdAndTagId(imageId, tagId);

        assertEquals(2, result.size());
        assertEquals(imageTagDtos, result);
    }

    @Test
    void testDeleteByImageId() {
        Long imageId = 1L;

        imageTagService.deleteByImageId(imageId);

        verify(imageTagRepository).deleteByImage_Id(imageId);
    }

    @Test
    void testDeleteByTagId() {
        Long tagId = 1L;

        imageTagService.deleteByTagId(tagId);

        verify(imageTagRepository).deleteByTag_Id(tagId);
    }

    @Test
    void testFindImageBySearchedTags_WithTags() {
        List<Long> tagIds = Arrays.asList(1L, 2L);
        List<ImageEntity> imageEntities = Arrays.asList(new ImageEntity(), new ImageEntity());
        List<ImageDto> imageDtos = Arrays.asList(new ImageDto(), new ImageDto());

        when(imageTagRepository.findImagesByAllTags(tagIds, tagIds.size())).thenReturn(imageEntities);
        when(imageMapper.mapTo(any(ImageEntity.class))).thenReturn(imageDtos.get(0), imageDtos.get(1));

        List<ImageDto> result = imageTagService.findImageBySearchedTags(tagIds);

        assertEquals(2, result.size());
        assertEquals(imageDtos, result);
    }

}