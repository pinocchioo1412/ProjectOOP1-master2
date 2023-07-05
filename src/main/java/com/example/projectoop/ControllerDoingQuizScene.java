package com.example.projectoop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class ControllerDoingQuizScene {
    private Stage stage;
    private Scene  scene;
    private Node anyNode;
    @FXML
    private Label timerLabel;
    @FXML
    private Label myLabel;

    private int countdownSeconds;

    public void setCountdownSeconds(int x){
        this.countdownSeconds=x;
    }
    public void initialize() throws IOException {
        int x=1;
        setCountdownSeconds(x);
        timerLabel.setText(formatTime(countdownSeconds));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            countdownSeconds--;
            if(countdownSeconds >= 0) {
                timerLabel.setText(formatTime(countdownSeconds));
            }
            else{
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FinishQuizScene.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) myLabel.getScene().getWindow();
                Scene scene1 = new Scene(root);
                stage.setScene(scene1);
                stage.show();
            }
        }));
        timeline.setCycleCount(x+1);
        timeline.play();

    }


    private String formatTime(int seconds) {
        int hours = seconds/3600;
        int minutes = seconds%3600/60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d",hours, minutes, secs);
    }
}


