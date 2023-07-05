package com.example.projectoop;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.*;

public class ControllerAddRandomQuestionScene {
    Stage stage;
    Scene scene;
    int height=150;
    @FXML
    AnchorPane questionBank;

    public void initialize(){
        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query ="SELECT question_name FROM question";
        Statement stm =null;
        try {
            // Tạo kết nối
            Connection conn = DriverManager.getConnection(DB_URL,USER_NAME, PASSWORD);
            // Thực hiện các truy vấn SQL
            stm = conn.createStatement();
            ResultSet rs =stm.executeQuery(query);
            while(rs.next()){
                String question_name = rs.getNString("question_name");
                Pane question1 =createQuestionPane(question_name);
                questionBank.setPrefSize(questionBank.getMaxWidth(),questionBank.getHeight()+30);
                questionBank.getChildren().add(question1);
                question1.setLayoutY(this.height);
                this.height=this.height+30;
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Pane createQuestionPane(String s){
        Pane question = new Pane();
        question.setPrefSize(960, 30);
        question.setStyle("-fx-background-color:  #ffffff;");

        CheckBox checkBox = new CheckBox();
        checkBox.setLayoutX(10);
        checkBox.setLayoutY(5);

        Label questionName = new Label();
        questionName.setText(s);
        questionName.setPrefSize(850, 25);
        questionName.setLayoutX(50);
        questionName.setLayoutY(0);

        Button edit = new Button();
        edit.setLayoutX(900);
        edit.setLayoutY(0);
        edit.setText("Edit");
        edit.setStyle("-fx-background-color:  #ffffff");

        question.getChildren().addAll(checkBox, questionName, edit);
        return question;
    }
}
