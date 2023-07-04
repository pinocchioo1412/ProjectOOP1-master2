package com.example.projectoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.Dragboard;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ControllerImportScene  {

    File file = new File("");
    @FXML
    private Label DropFile;
    @FXML
    private ImageView Warning;

    private Stage stage;
    private Scene scene;

    public void switchToSceneQuestion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("QuestionScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneCategories(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CategoriesScene.fxml")));
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
    @FXML
    void ChooseFile(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn tệp");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt"));
        file = fileChooser.showOpenDialog(Warning.getScene().getWindow());
        DropFile.setText(file.getName());
    }
    @FXML
    void DragFile(DragEvent event) {
        if (event.getGestureSource() != DropFile && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }
    @FXML
    void DragFile1(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            Warning.setVisible(false);
            file = db.getFiles().get(0);
            DropFile.setText(file.getName());
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void Import(ActionEvent event) {
        if (file.getName().endsWith(".txt") || file.getName().endsWith(".docx")) {
            CheckFileFormat.getInstance().checkFormat(file.getPath());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong format");
            alert.show();
        }
    }
    @FXML
    void DragFile3(DragEvent event) {
        if (event.getGestureSource() != DropFile && event.getDragboard().hasFiles()) {
            DropFile.setStyle("-fx-background-color: #1E90FF");
        }
        event.consume();
    }
    @FXML
    void DragFile4(DragEvent event) {
        DropFile.setStyle("-fx-background-color: #ffffff");
        event.consume();
    }


}

