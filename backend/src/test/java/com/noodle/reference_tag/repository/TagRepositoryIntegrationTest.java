package com.noodle.reference_tag.repository;

import com.noodle.reference_tag.TestDataUtil;
import com.noodle.reference_tag.domain.TagEntity;
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
public class TagRepositoryIntegrationTest {

    private TagRepository underTest;

    @Autowired
    public TagRepositoryIntegrationTest(TagRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatTagCanBeCreatedAndRecalled(){
        TagEntity tagEntity = TestDataUtil.createTestTagA();

        underTest.save(tagEntity);
        Optional<TagEntity> result = underTest.findById(tagEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(tagEntity);
    }

    @Test
    public void testThatMultipleTagsCanBeCreatedAndRecalled(){
        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        underTest.save(tagEntityA);

        TagEntity tagEntityB = TestDataUtil.createTestTagB();
        underTest.save(tagEntityB);

        TagEntity tagEntityC = TestDataUtil.createTestTagC();
        underTest.save(tagEntityC);

        List<TagEntity> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(tagEntityA, tagEntityB, tagEntityC);
    }

    @Test
    public void testThatTagCanBeUpdated(){
        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        underTest.save(tagEntityA);

        tagEntityA.setName("UPDATED NAME");
        underTest.save(tagEntityA);

        Optional<TagEntity> result = underTest.findById(tagEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(tagEntityA);
    }

    @Test
    public void testThatTagCanBeDeleted(){
        TagEntity tagEntityA = TestDataUtil.createTestTagA();
        underTest.save(tagEntityA);
        underTest.deleteById(tagEntityA.getId());
        Optional<TagEntity> result = underTest.findById(tagEntityA.getId());
        assertThat(result).isEmpty();
    }

}
