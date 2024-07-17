package com.noodle.reference_tag.controller;

import com.noodle.reference_tag.dto.ImageDto;
import com.noodle.reference_tag.dto.TagDto;
import com.noodle.reference_tag.service.ImageFrontendService;
import com.noodle.reference_tag.service.ImageTagFrontendService;
import com.noodle.reference_tag.service.TagFrontendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private final ImageFrontendService imageFrontendService;
    private final TagFrontendService tagFrontendService;
    private final ImageTagFrontendService imageTagFrontendService;

    @Autowired
    public MainController(ImageFrontendService imageFrontendService,
                          TagFrontendService tagFrontendService,
                          ImageTagFrontendService imageTagFrontendService) {
        this.imageFrontendService = imageFrontendService;
        this.tagFrontendService = tagFrontendService;
        this.imageTagFrontendService = imageTagFrontendService;
    }

    public void onTestButtonClick(){
//        ImageDto newImageDto = ImageDto.builder()
//                .path("new/path/wholesome")
//                .build();
//
//        imageFrontendService.createImage(newImageDto);

//        Optional<ImageDto> foundImageDto = imageFrontendService.getImage(52L);
//        System.out.println("Found DTO: " + foundImageDto);

//        List<ImageDto> allImagesDto = imageFrontendService.getAllImages();
//
//        for(ImageDto imageDto: allImagesDto){
//            System.out.println("Found DTO: " + imageDto);
//        }

//        imageFrontendService.deleteImage(52L);

//        Optional<ImageDto> imageByPath = imageFrontendService.getImageByPath("/path/to/your/image.jpg");
//        System.out.println("Found DTO: " + imageByPath);

//        TagDto newTagDto = TagDto.builder()
//                .name("hair")
//                .build();
//
//        tagFrontendService.createTag(newTagDto);

//        Optional<TagDto> tagDto = tagFrontendService.getTag(3L);
//        System.out.println("Found tag" + tagDto);

//        List<TagDto> allTags = tagFrontendService.getAllTags();
//        for(TagDto tagDto: allTags){
//            System.out.println("Found tag" + tagDto);
//        }

//        tagFrontendService.deleteTag(52L);

//        Optional<TagDto> tagDto = tagFrontendService.getTagByName("arm");
//        System.out.println("Found tag" + tagDto);


//        imageTagFrontendService.associateTagWithImage(102L, 1L);

//        imageTagFrontendService.removeTagFromImage(1L, 3L);

//        List<TagDto> tagsForImage = imageTagFrontendService.getTagsForImage(102L);
//        for(TagDto tagDto: tagsForImage){
//          System.out.println("Found tag" + tagDto);
//       }

//        List<Long> searchList = new ArrayList<>();
//        searchList.add(1L);
//        searchList.add(3L);
//
//        List<ImageDto> imagesByTags = imageTagFrontendService.findImagesByTags(searchList);
//
//        for(ImageDto imageDto: imagesByTags){
//            System.out.println("Found Image: " + imageDto);
//        }
    }


}
