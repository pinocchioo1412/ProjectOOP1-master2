package com.example.projectoop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        Button addButton = new Button("Thêm Pane");
        addButton.setOnAction(event -> {
            Pane newPane = createNewPane(1); // Tạo một Pane mới
            root.getChildren().add(newPane); // Thêm Pane vào AnchorPane
        });

        AnchorPane.setTopAnchor(addButton, 10.0);
        AnchorPane.setLeftAnchor(addButton, 10.0);

        root.getChildren().add(addButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setAddPicture(ActionEvent event){
        FileChooser fc = new FileChooser();
        File sellectedfile = fc.showOpenDialog(null);
    }
    private Pane createNewPane(int n){
        Pane newChoice = new Pane();
        newChoice.setPrefSize(400, 150);
        newChoice.setStyle("-fx-background-color:  #fffff0;");

        Label nameChoice = new Label();
        nameChoice.setText("Choice "+ n);
        nameChoice.setStyle("-fx-font-size: 12px;");
        nameChoice.setLayoutX(15);
        nameChoice.setLayoutY(10);

        TextField answer = new TextField();
        answer.setLayoutX(85);
        answer.setLayoutY(10);
        answer.setPrefSize(300, 50);

        Label grade = new Label("Grade");
        grade.setLayoutX(15);
        grade.setLayoutY(80);
        grade.setStyle("-fx-background-color:  #fffff0;");

        ComboBox<String> choosegrade = new ComboBox<String>();
        choosegrade.setLayoutX(85);
        choosegrade.setLayoutY(80);

        Button picture = new Button();
        picture.setLayoutX(253);
        picture.setLayoutY(80);
        picture.setText("ADD PICTURE");
        picture.setOnAction(event -> {
            setAddPicture(event);
        });

        newChoice.getChildren().addAll(nameChoice, answer, grade, choosegrade, picture);

        return newChoice;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
