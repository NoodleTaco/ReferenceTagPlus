package com.noodle.reference_tag.controller;

import com.noodle.reference_tag.config.StageInitializer;
import com.noodle.reference_tag.dto.ImageDto;
import com.noodle.reference_tag.dto.TagDto;
import com.noodle.reference_tag.service.ImageFrontendService;
import com.noodle.reference_tag.service.ImageTagFrontendService;
import com.noodle.reference_tag.service.TagFrontendService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.noodle.reference_tag.util.NotificationUtil;
import com.noodle.reference_tag.util.TagSelectionManager;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;


@Controller
public class MainController {
    private final ImageFrontendService imageFrontendService;
    private final TagFrontendService tagFrontendService;
    private final ImageTagFrontendService imageTagFrontendService;

    private final StageInitializer stageInitializer;

    private final TagSelectionManager tagSearchList;
    @Autowired
    public MainController(ImageFrontendService imageFrontendService,
                          TagFrontendService tagFrontendService,
                          ImageTagFrontendService imageTagFrontendService, StageInitializer stageInitializer, TagSelectionManager tagSearchList) {
        this.imageFrontendService = imageFrontendService;
        this.tagFrontendService = tagFrontendService;
        this.imageTagFrontendService = imageTagFrontendService;
        this.stageInitializer = stageInitializer;
        this.tagSearchList = tagSearchList;
    }

    //FXML Properties

    @FXML
    private ListView<TagDto> allTagListView;

    @FXML
    private TilePane imageTilePane;

    @FXML
    private ImageView selectedImageView;

    @FXML
    private Label selectedImageNameLabel;

    @FXML
    private ListView<TagDto> selectedImageTagListView;

    @FXML
    private ListView<TagDto> searchTagListView;


    //Properties holding selected elements in display
    private ImageDto selectedImage;

    public void initialize() {

        refreshAllTagListView();
        refreshImageTilePane();

        //Set on click callback for image display
        imageTilePane.setOnMouseClicked(event -> {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode instanceof ImageView) {
                ImageView clickedImageView = (ImageView) clickedNode;
                String imagePath = (String) clickedImageView.getUserData();
                selectedImage = imageFrontendService.getImageByPath(imagePath).orElse(null);
                if (selectedImage != null) {
                    refreshSelectedImageDetails();
                } else {
                    System.out.println("No image found for path: " + imagePath);
                }
            }
        });
    }

    private void refreshSelectedImageDetails() {
        if (selectedImage != null) {
            File imageFile = new File("src/main/resources/" + selectedImage.getPath());
            selectedImageView.setImage(new Image(imageFile.toURI().toString()));
            selectedImageNameLabel.setText(Paths.get(selectedImage.getPath()).getFileName().toString());
            refreshImageTags();
        }
        else{
            selectedImageView.setImage(null);
            selectedImageNameLabel.setText("");
            selectedImageTagListView.getItems().clear();
        }
    }

    private void refreshImageTags() {
        if(selectedImage != null){
            List<TagDto> tagsForImage = imageTagFrontendService.getTagsForImage(selectedImage.getId());
            selectedImageTagListView.getItems().setAll(tagsForImage);
        }
    }

    /**
     * Called when the Create Tag Button is activated
     *
     */
    @FXML
    public void onCreateTagAction(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create New Tag");
        dialog.setHeaderText("Enter the name for the new tag:");
        dialog.setContentText("Tag Name:");

        Optional<String> result = dialog.showAndWait();
        //If something was entered
        result.ifPresent(tagName -> {
            if (!tagName.isEmpty()) {
                Optional<TagDto> tagDto = tagFrontendService.getTagByName(tagName);

                //Tag with the same name already exists
                if(tagDto.isPresent()){
                    NotificationUtil.showNotification("A tag with this name already exists.", stageInitializer.getPrimaryStage());
                }
                else{
                    TagDto newTag = new TagDto();
                    newTag.setName(tagName);
                    tagFrontendService.createTag(newTag);
                    NotificationUtil.showNotification("Tag created successfully.", stageInitializer.getPrimaryStage());
                    refreshAllTagListView();
                }

            }
        });
    }

    @FXML
    public void onAddImageButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stageInitializer.getPrimaryStage());

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            Optional<ImageDto> existingImage = imageFrontendService.getImageByPath(imagePath);
            if (existingImage.isPresent()) {
                NotificationUtil.showNotification("Image already exists in the database.", stageInitializer.getPrimaryStage());
            } else {
                try {
                    // Get the path to the "images" directory within the project's resources
                    Path resourcesPath = Paths.get("src", "main", "resources");
                    Path imagesDir = resourcesPath.resolve("images");

                    // Copy the image file to the "images" directory
                    Path destinationPath = imagesDir.resolve(selectedFile.getName());
                    Files.copy(Paths.get(imagePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    // Store the relative path to the "images" directory
                    Path relativePath = Paths.get("images", selectedFile.getName());

                    ImageDto newImage = new ImageDto();
                    newImage.setPath(relativePath.toString()); // Save the relative path
                    imageFrontendService.createImage(newImage);
                    NotificationUtil.showNotification("Image added successfully.", stageInitializer.getPrimaryStage());
                    refreshImageTilePane();
                } catch (IOException e) {
                    e.printStackTrace(); // Print the stack trace
                }
            }
        }
    }


    /**
     * Populate the Tag List View using information in the DB
     */
    @FXML
    public void refreshAllTagListView(){
        List<TagDto> allTags = tagFrontendService.getAllTags();
        allTagListView.getItems().setAll(allTags);

        List<ImageDto> result = imageTagFrontendService.findImagesByTags(tagSearchList.getSelectedTagIds());

        for(ImageDto imageEntity: result){
            System.out.println(imageEntity);
        }
    }

    @FXML
    public void refreshSearchTagListView(){
        searchTagListView.getItems().setAll(tagSearchList.getSelectedTags());
    }

    private void refreshImageTilePane() {
        imageTilePane.getChildren().clear();
        List<ImageDto> images = imageTagFrontendService.findImagesByTags(tagSearchList.getSelectedTagIds());
        for (ImageDto imageDto : images) {
            try {
                File imageFile = new File("src/main/resources/" + imageDto.getPath());
                Image image = new Image(imageFile.toURI().toString());
                ImageView thumbnailView = new ImageView(image);
                thumbnailView.setFitHeight(100);
                thumbnailView.setFitWidth(100);
                thumbnailView.setUserData(imageDto.getPath()); // Store the path as user data
                imageTilePane.getChildren().add(thumbnailView);
            } catch (Exception e) {
                // Handle any exceptions that occur while loading the image
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addTagToImage() {
        if (selectedImage == null) {
            NotificationUtil.showNotification("No image selected.", stageInitializer.getPrimaryStage());
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Tag to Image");
        dialog.setHeaderText("Enter the name of the tag to add:");
        dialog.setContentText("Tag Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(tagName -> {
            if (!tagName.isEmpty()) {
                Optional<TagDto> foundTag = tagFrontendService.getTagByName(tagName);
                if(foundTag.isPresent()){
                    TagDto tag = foundTag.get();
                    System.out.println("Tag Found: " + tag);




                    if(imageTagFrontendService.findByImageIdAndTagId(selectedImage.getId(), tag.getId()).isEmpty()){
                        imageTagFrontendService.associateTagWithImage(selectedImage.getId(), tag.getId());
                        refreshImageTags();
                        NotificationUtil.showNotification("Tag added to image.", stageInitializer.getPrimaryStage());
                    }
                    else{
                        NotificationUtil.showNotification("Tag Already Associated with Image.", stageInitializer.getPrimaryStage());
                    }

                }
                else{
                    NotificationUtil.showNotification("Tag Addition Failed.", stageInitializer.getPrimaryStage());
                }

                System.out.println("Image Entity to String: " + selectedImage);

            }
        });
    }

    @FXML
    public void removeTagFromImage(){
        if (selectedImage == null) {
            NotificationUtil.showNotification("No image selected.", stageInitializer.getPrimaryStage());
            return;
        }

        TagDto selectedTag = selectedImageTagListView.getSelectionModel().getSelectedItem();
        if (selectedTag == null) {
            NotificationUtil.showNotification("No tag selected.", stageInitializer.getPrimaryStage());
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Tag Removal");
        confirmDialog.setHeaderText("Are you sure you want to remove this tag?");
        confirmDialog.setContentText("Tag: " + selectedTag.getName());

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            imageTagFrontendService.removeTagFromImage(selectedImage.getId(), selectedTag.getId());
            refreshImageTags();
            NotificationUtil.showNotification("Tag removed from image.", stageInitializer.getPrimaryStage());
        }
    }

    @FXML
    public void deleteTag(){
        TagDto selectedTag = allTagListView.getSelectionModel().getSelectedItem();
        if (selectedTag == null) {
            NotificationUtil.showNotification("No tag selected.", stageInitializer.getPrimaryStage());
            return;
        }
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Tag Deletion");
        confirmDialog.setHeaderText("Are you sure you want to delete this tag?");
        confirmDialog.setContentText("Tag: " + selectedTag.getName());

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tagFrontendService.deleteTag(selectedTag.getId());
            refreshAllTagListView();
            NotificationUtil.showNotification("Tag Deleted", stageInitializer.getPrimaryStage());
            refreshImageTags();
        }
    }

    @FXML
    public void deleteSelectedImage(){
        if(selectedImage == null){
            NotificationUtil.showNotification("No image selected.", stageInitializer.getPrimaryStage());
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Image Deletion");
        confirmDialog.setHeaderText("Are you sure you want to delete this Image?");
        confirmDialog.setContentText("Image: " + selectedImage.getPath());

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            String resourcePath = "src/main/resources/" + selectedImage.getPath();
            File imageFile = new File(resourcePath);
            if (imageFile.exists()) {
                boolean deleted = imageFile.delete();
                if (!deleted) {
                    NotificationUtil.showNotification("Failed to delete image file.", stageInitializer.getPrimaryStage());
                    return;
                }
            }

            imageFrontendService.deleteImage(selectedImage.getId());
            selectedImage = null;
            NotificationUtil.showNotification("Image Deleted", stageInitializer.getPrimaryStage());
            refreshImageTilePane();
            refreshSelectedImageDetails();
            refreshImageTags();
        }
    }

    @FXML
    public void addTagToSearch(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Tag to Image");
        dialog.setHeaderText("Enter the name of the tag to add:");
        dialog.setContentText("Tag Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(tagName -> {
            if (!tagName.isEmpty()) {
                Optional<TagDto> foundTag = tagFrontendService.getTagByName(tagName);
                if(foundTag.isPresent()){
                    TagDto tag = foundTag.get();

                    if(tagSearchList.addTag(tag)){
                        refreshSearchTagListView();
                        refreshImageTilePane();
                    }
                    else{
                        NotificationUtil.showNotification("Tag Already In Search.", stageInitializer.getPrimaryStage());
                    }

                }
                else{
                    NotificationUtil.showNotification("Tag Not Found.", stageInitializer.getPrimaryStage());
                }
            }
        });

    }

    @FXML
    public void removeTagFromSearch(){
        TagDto selectedTag = searchTagListView.getSelectionModel().getSelectedItem();
        if (selectedTag == null) {
            NotificationUtil.showNotification("No tag selected.", stageInitializer.getPrimaryStage());
            return;
        }
        tagSearchList.removeTag(selectedTag);
        refreshSearchTagListView();
        refreshImageTilePane();
        NotificationUtil.showNotification("Tag removed from Search.", stageInitializer.getPrimaryStage());
    }

}
