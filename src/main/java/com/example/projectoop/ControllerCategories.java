package com.example.projectoop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;

public class ControllerCategories implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private ImageView IDwarning;
    @FXML
    private ImageView namewarning;
    @FXML
    private TextField IDnumber;
    @FXML
    private TextArea CategoryInfo;
    @FXML
    private TextField Categoryname;

    public void switchToSceneQuestion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("QuestionScene.fxml"));
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
    public void switchToCreateQuizScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateQuizScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize(URL arg0, ResourceBundle arg1) {
        CategoryInfo.setWrapText(true);
        Categoryname.textProperty().addListener((Observable, oldvalue, newValue) -> {
            if (newValue.isEmpty()) {
                namewarning.setVisible(true);
            } else {
                namewarning.setVisible(false);
            }
        });
        IDnumber.textProperty().addListener((Observable, oldvalue, newValue) -> {
            if (newValue.isEmpty()) {
                IDwarning.setVisible(true);
            } else {
                IDwarning.setVisible(false);
            }
        });
    }

}
