package com.noodle.reference_tag;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.entity.ImageEntity;
import com.noodle.reference_tag.domain.entity.ImageTagEntity;
import com.noodle.reference_tag.domain.entity.TagEntity;

public final class TestDataUtil {

    public static ImageEntity createTestImageEntityA() {
        return ImageEntity.builder()
                .path("/path/to/image1.jpg")
                .build();
    }

    public static ImageEntity createTestImageEntityB() {
        return ImageEntity.builder()
                .path("/path/to/image2.jpg")
                .build();
    }

    public static ImageEntity createTestImageEntityC() {
        return ImageEntity.builder()
                .path("/path/to/image3.jpg")
                .build();
    }

    public static ImageDto createTestImageDtoA() {
        return ImageDto.builder()
                .id(1L)
                .path("/path/to/image1.jpg")
                .build();
    }

    public static ImageDto createTestImageDtoB() {
        return ImageDto.builder()
                .id(2L)
                .path("/path/to/image2.jpg")
                .build();
    }

    public static ImageDto createTestImageDtoC() {
        return ImageDto.builder()
                .id(3L)
                .path("/path/to/image3.jpg")
                .build();
    }



    public static TagEntity createTestTagA(){
        return TagEntity.builder()
                .name("Face")
                .build();
    }

    public static TagEntity createTestTagB(){
        return TagEntity.builder()
                .name("Hair")
                .build();
    }

    public static TagEntity createTestTagC(){
        return TagEntity.builder()
                .name("Arm")
                .build();
    }

    public static ImageTagEntity createTestImageTag(final ImageEntity imageEntity, final TagEntity tagEntity){
        return ImageTagEntity.builder()
                .image(imageEntity)
                .tag(tagEntity)
                .build();
    }




}
