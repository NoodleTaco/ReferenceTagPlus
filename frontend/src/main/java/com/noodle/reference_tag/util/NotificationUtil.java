package com.noodle.reference_tag.util;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NotificationUtil {

    /**
     * Create a semi transparent message that fades after 3 seconds (similar to a Toast)
     * @param message The Text of the message
     * @param owner The Context to display the message on
     */
    public static void showNotification(String message, javafx.stage.Window owner) {
        Stage notificationStage = new Stage();
        notificationStage.setResizable(false);
        notificationStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-padding: 15px;");
        label.setOpacity(0.9);

        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: transparent;");
        root.setPrefSize(500, 50); // Set a preferred size for the notification
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        notificationStage.setScene(scene);

        if (owner != null) {
            notificationStage.initOwner(owner);
            notificationStage.setX(owner.getX() + owner.getWidth() / 2 - 100);
            notificationStage.setY(owner.getY() + owner.getHeight() - 100);
        } else {
            notificationStage.centerOnScreen();
        }

        notificationStage.show();

        // Hide the notification after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> notificationStage.close());
        delay.play();
    }
}