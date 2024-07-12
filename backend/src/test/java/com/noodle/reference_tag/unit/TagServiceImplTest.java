//package com.noodle.reference_tag.unit;
//
//import com.noodle.reference_tag.domain.entity.TagEntity;
//import com.noodle.reference_tag.repository.TagRepository;
//import com.noodle.reference_tag.service.ImageTagService;
//import com.noodle.reference_tag.service.impl.TagServiceImpl;
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
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TagServiceImplTest {
//
//    @Mock
//    private TagRepository tagRepository;
//
//    @InjectMocks
//    private TagServiceImpl tagService;
//
//    private TagEntity testTagEntity;
//
//    @Mock
//    ImageTagService imageTagService;
//
//    @BeforeEach
//    void setUp() {
//        testTagEntity = TagEntity.builder()
//                .id(1L)
//                .name("Test Tag")
//                .build();
//    }
//
//    @Test
//    void testSaveTag() {
//        when(tagRepository.save(testTagEntity)).thenReturn(testTagEntity);
//
//        TagEntity savedTagEntity = tagService.save(testTagEntity);
//
//        assertNotNull(savedTagEntity);
//        assertEquals(testTagEntity, savedTagEntity);
//        verify(tagRepository, times(1)).save(testTagEntity);
//    }
//
//    @Test
//    void testFindTagByIdWithExistingId() {
//        Long existingTagId = 1L;
//        when(tagRepository.findById(existingTagId)).thenReturn(Optional.of(testTagEntity));
//
//        Optional<TagEntity> foundTagEntity = tagService.findTagById(existingTagId);
//
//        assertTrue(foundTagEntity.isPresent());
//        assertEquals(testTagEntity, foundTagEntity.get());
//        verify(tagRepository, times(1)).findById(existingTagId);
//    }
//
//    @Test
//    void testFindTagByIdWithNonExistingId() {
//        Long nonExistingTagId = 2L;
//        when(tagRepository.findById(nonExistingTagId)).thenReturn(Optional.empty());
//
//        Optional<TagEntity> foundTagEntity = tagService.findTagById(nonExistingTagId);
//
//        assertFalse(foundTagEntity.isPresent());
//        verify(tagRepository, times(1)).findById(nonExistingTagId);
//    }
//
//    @Test
//    void testFindAllTags() {
//        List<TagEntity> expectedTagEntities = Collections.singletonList(testTagEntity);
//        when(tagRepository.findAll()).thenReturn(expectedTagEntities);
//
//        List<TagEntity> actualTagEntities = tagService.findAllTags();
//
//        assertEquals(expectedTagEntities, actualTagEntities);
//        verify(tagRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testIsExistsWithExistingId() {
//        Long existingTagId = 1L;
//        when(tagRepository.existsById(existingTagId)).thenReturn(true);
//
//        boolean exists = tagService.isExists(existingTagId);
//
//        assertTrue(exists);
//        verify(tagRepository, times(1)).existsById(existingTagId);
//    }
//
//    @Test
//    void testIsExistsWithNonExistingId() {
//        Long nonExistingTagId = 2L;
//        when(tagRepository.existsById(nonExistingTagId)).thenReturn(false);
//
//        boolean exists = tagService.isExists(nonExistingTagId);
//
//        assertFalse(exists);
//        verify(tagRepository, times(1)).existsById(nonExistingTagId);
//    }
//
//    @Test
//    void testDeleteTagById() {
//        //TODO: Doesn't work
//        Long tagIdToDelete = 1L;
//
//        tagService.deleteTagById(tagIdToDelete);
//
//        verify(tagRepository, times(1)).deleteById(tagIdToDelete);
//    }
//}