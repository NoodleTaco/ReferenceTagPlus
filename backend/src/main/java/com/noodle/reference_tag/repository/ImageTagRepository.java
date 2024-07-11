package com.noodle.reference_tag.repository;

import com.noodle.reference_tag.domain.ImageEntity;
import com.noodle.reference_tag.domain.ImageTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageTagRepository extends JpaRepository<ImageTagEntity, Long> {

    Optional<ImageTagEntity> findByImageIdAndTagId(Long imageId, Long tagId);

    @Query("SELECT it FROM ImageTagEntity it WHERE it.image.id = :imageId")
    List<ImageTagEntity> findByImageId(@Param("imageId") Long imageId);

    List<ImageTagEntity> findByImage_IdAndTag_Id(Long imageId, Long tagId);

    void deleteByImage_Id(Long imageId);

    void deleteByTag_Id(Long tagId);

    /**
     * Searches for Images that at least match the given number of tags
     * @param tagIds The List of Tag Entities used to specify the search
     * @param tagCount Number of matches in the List (should always be Tag List size)
     * @return The List of Image Entities that adhere to the search
     */
    @Query("SELECT DISTINCT it.image FROM ImageTagEntity it " +
            "WHERE it.tag.id IN :tagIds " +
            "AND it.image.id IN (" +
            "    SELECT it2.image.id FROM ImageTagEntity it2 " +
            "    WHERE it2.tag.id IN :tagIds " +
            "    GROUP BY it2.image.id " +
            "    HAVING COUNT(DISTINCT it2.tag.id) = :tagCount" +
            ")")
    List<ImageEntity> findImagesByAllTags(@Param("tagIds") List<Long> tagIds, @Param("tagCount") long tagCount);
}
