package com.noodle.reference_tag.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "image_tag")
/**
 * Entity Class representing the image_tag table in the db
 */
public class ImageTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_tag_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    

}
