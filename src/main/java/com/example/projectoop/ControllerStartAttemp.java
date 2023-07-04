package com.example.projectoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.EventObject;

public class ControllerStartAttemp {
    Stage stage;

    Scene scene;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void StartClick(ActionEvent event) throws IOException {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                ((Stage) window).close();
            }
        }
        Parent root = FXMLLoader.load(getClass().getResource("DoingQuizScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        double centerX = (screenWidth - stageWidth) / 2;
        double centerY = (screenHeight - stageHeight) / 2;

        stage.setX(centerX);
        stage.setY(centerY);
        stage.show();
    }

    public void cancelCLick(ActionEvent event) throws IOException{
        Node sourceNode = (Node) event.getSource();
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        currentStage.close();
    }
}
