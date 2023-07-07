package com.example.projectoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ControllerEdittingQuizScene {
    private Stage stage;
    private Scene scene;

    @FXML
    private AnchorPane questionBank;

    private  int height=0;

    public void initialize() throws SQLException {
        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query ="select question_name, question_id from question where question_id in(select question_id from quiz_question where quiz_id in (select quiz_id from quiz_in_progress))";
        Statement stm =null;

        Connection conn = DriverManager.getConnection(DB_URL,USER_NAME, PASSWORD);
        stm = conn.createStatement();
        ResultSet rs1=stm.executeQuery(query);
        while(rs1.next()){
                String question_name = rs1.getNString("QUESTION_NAME");
                int question_id = rs1.getInt("QUESTION_ID");
                Pane question1 =createQuestionPane(question_name,question_id);
                questionBank.setPrefSize(questionBank.getMaxWidth(),questionBank.getHeight()+30);
                questionBank.getChildren().add(question1);
                question1.setLayoutY(this.height);
                this.height=this.height+30;
            }
        conn.close();

    }
    public Pane createQuestionPane(String s, int n){
        Pane question = new Pane();
        question.setPrefSize(960, 30);
        question.setStyle("-fx-background-color:  #ffffff;");

        CheckBox checkBox = new CheckBox();
        checkBox.setLayoutX(10);
        checkBox.setLayoutY(5);
        checkBox.setText(""+n);

        Label questionName = new Label();
        questionName.setText(s);
        questionName.setPrefSize(850, 25);
        questionName.setLayoutX(50);
        questionName.setLayoutY(2);

        Button edit = new Button();
        edit.setLayoutX(900);
        edit.setLayoutY(0);
        edit.setText("Edit");
        edit.setStyle("-fx-background-color:  #ffffff");

        question.getChildren().addAll(checkBox, questionName, edit);
        return question;
    }

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
