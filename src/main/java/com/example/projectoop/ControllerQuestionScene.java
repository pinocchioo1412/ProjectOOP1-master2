package com.example.projectoop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerQuestionScene {
    public ComboBox<String> CategoryBox;
    private ObservableList<String> Categoreis = FXCollections.observableArrayList("Defaults");
    private Stage stage;
    private Scene scene;

    public void addCategory(String Category){
        Categoreis.add(Category);
    }

    @FXML
    public void initialize(){
        CategoryBox.setItems(Categoreis);
        CategoryBox.setValue("Defaults");
    }
    public void switchToSceneCategories(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CategoriesScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneImport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ImportScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneCreateQuestion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateQuestionScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToCreateQuizScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateQuizScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void createQuestionPane(String s){
        Pane question = new Pane();
        question.setPrefSize(960, 30);

        CheckBox checkBox = new CheckBox();
        checkBox.setLayoutX(10);
        checkBox.setLayoutY(5);

        Label questionName = new Label();
        questionName.setText(s);
        questionName.setPrefSize(850, 25);
        questionName.setLayoutX(30);
        questionName.setLayoutY(5);

        Button edit = new Button();
        edit.setLayoutX(900);
        edit.setLayoutY(5);
        edit.setText("Edit");

        question.getChildren().addAll(checkBox, questionName, edit);
    }
}
