package com.noodle.reference_tag;

import com.noodle.reference_tag.domain.ImageEntity;
import com.noodle.reference_tag.domain.ImageTagEntity;
import com.noodle.reference_tag.domain.TagEntity;

public final class TestDataUtil {

    public static ImageEntity createTestImageA() {
        return ImageEntity.builder()
                .path("/path/to/image1.jpg")
                .build();
    }

    public static ImageEntity createTestImageB() {
        return ImageEntity.builder()
                .path("/path/to/image2.jpg")
                .build();
    }

    public static ImageEntity createTestImageC() {
        return ImageEntity.builder()
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
