package com.example.projectoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.EventObject;

public class ControllerStartAttemp {
    Stage stage;

    Scene scene;

    @FXML
    Label label;

    public void initialize(){
        label.setPrefSize(500, 60);
        label.setWrapText(true);
        label.setText("Your attempt will have a time limit of 1 hour. When you start, the timer will begin to count down and can not ba paused. You must finish your attempt before it expires. Are you sure you wish start now");
    }
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
