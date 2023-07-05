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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class ControllerQuestionScene {
    public ComboBox<String> CategoryBox;
    private ObservableList<String> Categoreis = FXCollections.observableArrayList("Defaults");
    private Stage stage;
    private Scene scene;
    int height=0;
    @FXML
    AnchorPane questionBank;

    public void addCategory(String Category){
        Categoreis.add(Category);
    }

    @FXML
    public void initialize(){
        CategoryBox.setItems(Categoreis);
        CategoryBox.setValue("Default");
        String DB_URL = "jdbc:sqlserver://localhost:1433;encrypt=true;";
        String USER_NAME = "pinocchio";
        String PASSWORD = "pinocchio1412";
        String query ="SELECT CATEGORY_ID FROM CATEGORY";
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
