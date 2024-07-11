package com.noodle.reference_tag.repository;

import com.noodle.reference_tag.TestDataUtil;
import com.noodle.reference_tag.domain.ImageEntity;
import com.noodle.reference_tag.domain.ImageTagEntity;
import com.noodle.reference_tag.domain.TagEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ImageTagRepositoryIntegrationTest {

    private final ImageTagRepository underTest;

    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private final TagRepository tagRepository;

    @Autowired
    public ImageTagRepositoryIntegrationTest(ImageTagRepository underTest, ImageRepository imageRepository, TagRepository tagRepository) {
        this.underTest = underTest;
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
    }

    @Test
    @Transactional
    public void testThatImageTagCanBeCreatedAndRecalled(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        imageRepository.save(imageEntityA);
        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        tagRepository.save(tagEntityA);

        ImageTagEntity imageTagEntityA = TestDataUtil.createTestImageTag(imageEntityA, tagEntityA);

        underTest.save(imageTagEntityA);


        Optional<ImageTagEntity> result = underTest.findByImageIdAndTagId(imageEntityA.getId(), tagEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(imageTagEntityA);
    }

    @Test
    @Transactional
    public void testThatImageTagCanBeDeleted(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        imageRepository.save(imageEntityA);

        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        tagRepository.save(tagEntityA);

        ImageTagEntity imageTagEntityA = TestDataUtil.createTestImageTag(imageEntityA, tagEntityA);

        underTest.save(imageTagEntityA);

        ImageTagEntity foundImageTag = underTest.findByImageIdAndTagId(imageEntityA.getId(), tagEntityA.getId())
                .orElseThrow(() -> new RuntimeException("Image-Tag association not found"));

        underTest.delete(foundImageTag);

        Optional<ImageTagEntity> result = underTest.findByImageIdAndTagId(imageEntityA.getId(), tagEntityA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatMultipleImageTagsAssociatedWithImageCanBeRecalled() {
        // Create entities without setting the ID
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        TagEntity tagEntityB = TestDataUtil.createTestTagB();
        TagEntity tagEntityC = TestDataUtil.createTestTagC();

        // Save ImageEntity and TagEntity instances first
        imageRepository.save(imageEntityA);
        tagRepository.save(tagEntityA);
        tagRepository.save(tagEntityB);
        tagRepository.save(tagEntityC);

        // Use the managed entities to create ImageTagEntity instances
        ImageTagEntity imageTagEntityA = TestDataUtil.createTestImageTag(imageEntityA, tagEntityA);
        ImageTagEntity imageTagEntityB = TestDataUtil.createTestImageTag(imageEntityA, tagEntityB);
        ImageTagEntity imageTagEntityC = TestDataUtil.createTestImageTag(imageEntityA, tagEntityC);

        // Save ImageTagEntity instances
        underTest.save(imageTagEntityA);
        underTest.save(imageTagEntityB);
        underTest.save(imageTagEntityC);

        // Retrieve and assert
        List<ImageTagEntity> result = underTest.findByImageId(imageEntityA.getId());

        assertThat(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(imageTagEntityA, imageTagEntityB, imageTagEntityC);
    }

    @Test
    @Transactional
    public void testThatImageSearchByTagReturnsImagesWhenCriteriaExactlyMet(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        ImageEntity imageEntityB = TestDataUtil.createTestImageB();
        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        TagEntity tagEntityB = TestDataUtil.createTestTagB();

        imageRepository.save(imageEntityA);
        imageRepository.save(imageEntityB);
        tagRepository.save(tagEntityA);
        tagRepository.save(tagEntityB);

        //Both images have both tags
        ImageTagEntity imageTagEntityA = TestDataUtil.createTestImageTag(imageEntityA, tagEntityA);
        ImageTagEntity imageTagEntityB = TestDataUtil.createTestImageTag(imageEntityA, tagEntityB);
        ImageTagEntity imageTagEntityC = TestDataUtil.createTestImageTag(imageEntityB, tagEntityA);
        ImageTagEntity imageTagEntityD = TestDataUtil.createTestImageTag(imageEntityB, tagEntityB);

        underTest.save(imageTagEntityA);
        underTest.save(imageTagEntityB);
        underTest.save(imageTagEntityC);
        underTest.save(imageTagEntityD);

        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(tagEntityA.getId());
        tagIdList.add(tagEntityB.getId());

        List<ImageEntity> result = underTest.findImagesByAllTags(tagIdList, tagIdList.size());
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(imageEntityA, imageEntityB);
    }

    @Test
    @Transactional
    public void testThatImageSearchByTagReturnsImagesWhenCriteriaMetWithExtra(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        ImageEntity imageEntityB = TestDataUtil.createTestImageB();

        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        TagEntity tagEntityB = TestDataUtil.createTestTagB();
        TagEntity tagEntityC = TestDataUtil.createTestTagC();

        imageRepository.save(imageEntityA);
        imageRepository.save(imageEntityB);
        tagRepository.save(tagEntityA);
        tagRepository.save(tagEntityB);
        tagRepository.save(tagEntityC);

        ImageTagEntity imageTagEntityA = TestDataUtil.createTestImageTag(imageEntityA, tagEntityA);
        ImageTagEntity imageTagEntityB = TestDataUtil.createTestImageTag(imageEntityA, tagEntityB);
        ImageTagEntity imageTagEntityC = TestDataUtil.createTestImageTag(imageEntityB, tagEntityA);
        ImageTagEntity imageTagEntityD = TestDataUtil.createTestImageTag(imageEntityB, tagEntityB);
        ImageTagEntity imageTagEntityE = TestDataUtil.createTestImageTag(imageEntityB, tagEntityC);

        underTest.save(imageTagEntityA);
        underTest.save(imageTagEntityB);
        underTest.save(imageTagEntityC);
        underTest.save(imageTagEntityD);
        underTest.save(imageTagEntityE);

        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(tagEntityA.getId());
        tagIdList.add(tagEntityB.getId());

        List<ImageEntity> result = underTest.findImagesByAllTags(tagIdList, tagIdList.size());
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(imageEntityA, imageEntityB);
    }

    @Test
    @Transactional
    public void testThatImageSearchByTagReturnsNopImagesWhenCriteriaPartiallyMet(){
        ImageEntity imageEntityA = TestDataUtil.createTestImageA();
        ImageEntity imageEntityB = TestDataUtil.createTestImageB();

        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        TagEntity tagEntityB = TestDataUtil.createTestTagB();
        TagEntity tagEntityC = TestDataUtil.createTestTagC();

        imageRepository.save(imageEntityA);
        imageRepository.save(imageEntityB);
        tagRepository.save(tagEntityA);
        tagRepository.save(tagEntityB);
        tagRepository.save(tagEntityC);

        ImageTagEntity imageTagEntityA = TestDataUtil.createTestImageTag(imageEntityA, tagEntityA);
        ImageTagEntity imageTagEntityB = TestDataUtil.createTestImageTag(imageEntityA, tagEntityB);
        ImageTagEntity imageTagEntityC = TestDataUtil.createTestImageTag(imageEntityB, tagEntityB);
        ImageTagEntity imageTagEntityD = TestDataUtil.createTestImageTag(imageEntityB, tagEntityC);

        underTest.save(imageTagEntityA);
        underTest.save(imageTagEntityB);
        underTest.save(imageTagEntityC);
        underTest.save(imageTagEntityD);

        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(tagEntityA.getId());
        tagIdList.add(tagEntityC.getId());

        List<ImageEntity> result = underTest.findImagesByAllTags(tagIdList, tagIdList.size());
        assertThat(result)
                .isEmpty();
    }

}
