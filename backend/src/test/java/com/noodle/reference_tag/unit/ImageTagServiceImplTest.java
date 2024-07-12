//package com.noodle.reference_tag.unit;
//
//import com.noodle.reference_tag.domain.entity.ImageEntity;
//import com.noodle.reference_tag.domain.entity.ImageTagEntity;
//import com.noodle.reference_tag.domain.entity.TagEntity;
//import com.noodle.reference_tag.repository.ImageRepository;
//import com.noodle.reference_tag.repository.ImageTagRepository;
//import com.noodle.reference_tag.repository.TagRepository;
//import com.noodle.reference_tag.service.impl.ImageTagServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ImageTagServiceImplTest {
//
//    @Mock
//    private ImageRepository imageRepository;
//
//    @Mock
//    private TagRepository tagRepository;
//
//    @Mock
//    private ImageTagRepository imageTagRepository;
//
//    @InjectMocks
//    private ImageTagServiceImpl imageTagService;
//
//    private ImageEntity testImageEntity;
//    private TagEntity testTagEntity;
//    private ImageTagEntity testImageTagEntity;
//
//    @BeforeEach
//    void setUp() {
//        testImageEntity = ImageEntity.builder().id(1L).path("/path/to/image.jpg").build();
//        testTagEntity = TagEntity.builder().id(1L).name("Test Tag").build();
//        testImageTagEntity = ImageTagEntity.builder().id(1L).image(testImageEntity).tag(testTagEntity).build();
//    }
//
//    @Test
//    void testAssociateTagWithImage() {
//        when(imageRepository.findById(testImageEntity.getId())).thenReturn(Optional.of(testImageEntity));
//        when(tagRepository.findById(testTagEntity.getId())).thenReturn(Optional.of(testTagEntity));
//        when(imageTagRepository.save(any(ImageTagEntity.class))).thenReturn(testImageTagEntity);
//
//        ImageTagEntity savedImageTagEntity = imageTagService.associateTagWithImage(testImageEntity.getId(), testTagEntity.getId());
//
//        assertNotNull(savedImageTagEntity);
//        assertEquals(testImageTagEntity, savedImageTagEntity);
//        verify(imageTagRepository, times(1)).save(any(ImageTagEntity.class));
//    }
//
//    @Test
//    void testRemoveTagFromImage() {
//        when(imageTagRepository.findByImageIdAndTagId(testImageEntity.getId(), testTagEntity.getId()))
//                .thenReturn(Optional.of(testImageTagEntity));
//
//        imageTagService.removeTagFromImage(testImageEntity.getId(), testTagEntity.getId());
//
//        verify(imageTagRepository, times(1)).delete(testImageTagEntity);
//    }
//
//    @Test
//    void testGetTagsForImage() {
//        when(imageRepository.findById(testImageEntity.getId())).thenReturn(Optional.of(testImageEntity));
//        when(imageTagRepository.findByImageId(testImageEntity.getId())).thenReturn(Collections.singletonList(testImageTagEntity));
//
//        List<TagEntity> tagsForImage = imageTagService.getTagsForImage(testImageEntity.getId());
//
//        assertNotNull(tagsForImage);
//        assertEquals(1, tagsForImage.size());
//        assertEquals(testTagEntity, tagsForImage.get(0));
//    }
//}
