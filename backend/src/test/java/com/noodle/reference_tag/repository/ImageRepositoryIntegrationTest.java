package com.noodle.reference_tag.repository;

import com.noodle.reference_tag.TestDataUtil;
import com.noodle.reference_tag.domain.ImageEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ImageRepositoryIntegrationTest {

    private final ImageRepository underTest;

    @Autowired
    public ImageRepositoryIntegrationTest(ImageRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatImageCanBeCreatedAndRecalled(){
        ImageEntity imageEntity = TestDataUtil.createTestImageA();

        underTest.save(imageEntity);
        Optional<ImageEntity> result = underTest.findById(imageEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(imageEntity);
    }

    @Test
    public void testThatMultipleImagesCanBeCreatedAndRecalled(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        underTest.save(imageEntityA);

        ImageEntity imageEntityB = TestDataUtil.createTestImageB();
        underTest.save(imageEntityB);

        ImageEntity imageEntityC = TestDataUtil.createTestImageC();
        underTest.save(imageEntityC);

        List<ImageEntity> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(imageEntityA, imageEntityB, imageEntityC);
    }

    @Test
    public void testThatImageCanBeUpdated(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        underTest.save(imageEntityA);

        imageEntityA.setPath("UPDATED PATH");
        underTest.save(imageEntityA);

        Optional<ImageEntity> result = underTest.findById(imageEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(imageEntityA);
    }

    @Test
    public void testThatImageCanBeDeleted(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        underTest.save(imageEntityA);
        underTest.deleteById(imageEntityA.getId());
        Optional<ImageEntity> result = underTest.findById(imageEntityA.getId());
        assertThat(result).isEmpty();
    }

}
