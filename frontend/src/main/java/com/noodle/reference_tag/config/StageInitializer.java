package com.noodle.reference_tag.config;

import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer {
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}