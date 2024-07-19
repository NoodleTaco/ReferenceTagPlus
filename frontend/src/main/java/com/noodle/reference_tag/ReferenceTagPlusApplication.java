package com.noodle.reference_tag;

import com.noodle.reference_tag.config.StageInitializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ReferenceTagPlusApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent root;

    @Override
    public void init() throws Exception {
        springContext = new SpringApplicationBuilder(ReferenceTagPlusMain.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the primary stage in StageInitializer
        springContext.getBean(StageInitializer.class).setPrimaryStage(primaryStage);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        root = fxmlLoader.load();

        primaryStage.setTitle("Reference Tag Application");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
        Platform.exit();
    }
}