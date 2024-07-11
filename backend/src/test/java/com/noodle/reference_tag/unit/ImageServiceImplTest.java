package com.noodle.reference_tag.unit;

import com.noodle.reference_tag.domain.ImageEntity;
import com.noodle.reference_tag.repository.ImageRepository;
import com.noodle.reference_tag.service.impl.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * These tests create mock instances of ImageRepository and injects them into ImageServiceImpl
 * Each method sets up the expected behavior using when() method, then calls the method under test on the ImageServiceImpl instance
 * The expected behavior is asserted with the verify method
 */
@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    private ImageEntity testImageEntity;

    //Before each sets up a test ImageEntity instance that can be used across multiple test cases
    @BeforeEach
    void setUp(){
        testImageEntity = ImageEntity.builder()
                .id(1L)
                .path("/path/to/image.jpg")
                .build();
    }

    @Test
    void testSaveImage(){
        //Set up the mock behavior for the save method of the ImageRepository
        //Specifies when the save method is called with testImageEntity as an arg, it should return the same testImageEntity object
        when(imageRepository.save(testImageEntity)).thenReturn(testImageEntity);

        //Calls the method under test and stores its result
        ImageEntity savedImageEntity = imageService.save(testImageEntity);

        //These lines assert that the savedImageEntity is not null and equal to the original, ensuring the expected return
        assertNotNull(savedImageEntity);
        assertEquals(testImageEntity, savedImageEntity);

        //Verifies that the save method of the mocked ImageRepository was called exactly once with testImageEntity as an arg
        verify(imageRepository, times(1)).save(testImageEntity);
    }

    @Test
    void testFindImageByIdWithExistingId() {
        Long existingImageId = 1L;
        when(imageRepository.findById(existingImageId)).thenReturn(Optional.of(testImageEntity));

        Optional<ImageEntity> foundImageEntity = imageService.findImageById(existingImageId);

        assertTrue(foundImageEntity.isPresent());
        assertEquals(testImageEntity, foundImageEntity.get());
        verify(imageRepository, times(1)).findById(existingImageId);
    }

    @Test
    void testFindImageByIdWithNonExistingId() {
        Long nonExistingImageId = 2L;
        when(imageRepository.findById(nonExistingImageId)).thenReturn(Optional.empty());

        Optional<ImageEntity> foundImageEntity = imageService.findImageById(nonExistingImageId);

        assertFalse(foundImageEntity.isPresent());
        verify(imageRepository, times(1)).findById(nonExistingImageId);
    }

    @Test
    void testFindAllImages() {
        List<ImageEntity> expectedImageEntities = Collections.singletonList(testImageEntity);
        when(imageRepository.findAll()).thenReturn(expectedImageEntities);

        List<ImageEntity> actualImageEntities = imageService.findAllImages();

        assertEquals(expectedImageEntities, actualImageEntities);
        verify(imageRepository, times(1)).findAll();
    }

    @Test
    void testIsExistsWithExistingId() {
        Long existingImageId = 1L;
        when(imageRepository.existsById(existingImageId)).thenReturn(true);

        boolean exists = imageService.isExists(existingImageId);

        assertTrue(exists);
        verify(imageRepository, times(1)).existsById(existingImageId);
    }

    @Test
    void testIsExistsWithNonExistingId() {
        Long nonExistingImageId = 2L;
        when(imageRepository.existsById(nonExistingImageId)).thenReturn(false);

        boolean exists = imageService.isExists(nonExistingImageId);

        assertFalse(exists);
        verify(imageRepository, times(1)).existsById(nonExistingImageId);
    }

    @Test
    void testDeleteImageById() {
        //TODO: Doesn't work

        Long imageIdToDelete = 1L;

        imageService.deleteImageById(imageIdToDelete);

        verify(imageRepository, times(1)).deleteById(imageIdToDelete);
    }



}
