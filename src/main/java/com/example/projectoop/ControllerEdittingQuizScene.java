package com.example.projectoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerEdittingQuizScene {
    private Stage stage;
    private Scene scene;

    public void saveClickAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AttempQuizScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void addNewQuestionEvent (ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddQuestionScene.fxml"));
        Scene secondaryScene = new Scene(root);
        stage.setScene(secondaryScene);

        stage.show();
    }
    public void addRandomQuestionEvent (ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddRandomQuestionScene.fxml"));
        Scene secondaryScene = new Scene(root);
        stage.setScene(secondaryScene);
        stage.show();


    }

}
