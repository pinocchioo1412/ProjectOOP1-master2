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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ControllerCreateQuetions {
    @FXML
    Button addPicture;

    int numberchoice = 2;

    int height = 500;

    Stage stage;

    Scene scene;

    @FXML
    AnchorPane scrollPane;

    @FXML
    Button SaveChange;

    @FXML
    Button SaveChangeAndContinue;
    @FXML
    Button Cancel;

    @FXML
    Button BlankMore3Choices;

    @FXML
    ComboBox<String> Choices1Grades;
    @FXML
    ComboBox<String> Choices2Grades;

    private ObservableList<String> grades = FXCollections.observableArrayList("None", "100%","90%", "83.333333%", "80%", "75%", "70%", "66.666667%", "60%", "50%", "40%", "33.333333%","30%", "20%", "16.666667%", "14.28571%", "12.5%","11.111111%", "10%", "5%", "-5%", "None" );

    public void setAddPicture(ActionEvent event){
        FileChooser fc = new FileChooser();
        File sellectedfile = fc.showOpenDialog(null);
    }

    @FXML
    public void initialize(){
        Choices2Grades.setItems(grades);
        Choices1Grades.setItems(grades);
        Choices1Grades.setValue("None");
        Choices2Grades.setValue("None");
    }

    public void addMore3Choices(ActionEvent event){
        scrollPane.setPrefSize(scrollPane.getWidth(), scrollPane.getHeight()+465);
        BlankMore3Choices.setLayoutY(BlankMore3Choices.getLayoutY()+465);
        SaveChange.setLayoutY(SaveChange.getLayoutY()+465);
        SaveChangeAndContinue.setLayoutY(SaveChangeAndContinue.getLayoutY()+465);
        Cancel.setLayoutY(Cancel.getLayoutY()+465);;
        int count=1;
        while(count<=3){
            this.numberchoice=this.numberchoice+1;
            Pane newchoice = createNewChoice(numberchoice);
            newchoice.setLayoutX(400);
            this.height=this.height+155;
            newchoice.setLayoutY(this.height);
            scrollPane.getChildren().add(newchoice);
            count++;
        }
    }
    private Pane createNewChoice(int n){
        Pane newChoice = new Pane();
        newChoice.setPrefSize(400, 150);
        newChoice.setStyle("-fx-background-color:  #D3D3D3;");

        Label nameChoice = new Label();
        nameChoice.setText("Choice "+ n);
        nameChoice.setStyle("-fx-font-size: 12px;");
        nameChoice.setLayoutX(15);
        nameChoice.setLayoutY(10);
        nameChoice.setStyle("-fx-background-color:  #D3D3D3;");

        TextField answer = new TextField();
        answer.setLayoutX(85);
        answer.setLayoutY(10);
        answer.setPrefSize(300, 50);

        Label grade = new Label("Grade");
        grade.setLayoutX(15);
        grade.setLayoutY(80);
        grade.setStyle("-fx-background-color:  #D3D3D3;");

        ComboBox<String> choosegrade = new ComboBox<String>();
        choosegrade.setLayoutX(85);
        choosegrade.setLayoutY(80);
        double x = choosegrade.getHeight();
        choosegrade.setPrefSize(150 ,x );
        choosegrade.setItems(grades);
        choosegrade.setValue("None");

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

    public void ClickCancelEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("QuestionScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void ClickSaveChangeEvent(ActionEvent event) throws  IOException{
        Parent root = FXMLLoader.load(getClass().getResource("QuestionScene.fxml"));
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
}

