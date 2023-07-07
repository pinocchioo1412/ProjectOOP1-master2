package com.example.projectoop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.*;

public class ControllerAddQuestionScene {
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
        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query ="SELECT CATEGORY_ID,CATEGORY_NAME FROM CATEGORY";
        Statement stm =null;
        try {
            // Tạo kết nối
            Connection conn = DriverManager.getConnection(DB_URL,USER_NAME, PASSWORD);
            // Thực hiện các truy vấn SQL
            stm = conn.createStatement();
            ResultSet rs =stm.executeQuery(query);
            while (rs.next()){
                String category_name =rs.getNString("CATEGORY_NAME");
                System.out.println(category_name);
                addCategory(category_name);
            }
            CategoryBox.setItems(Categoreis);
            CategoryBox.setValue("Default");
            CategoryBox.setOnAction(event -> {
                this.height=0;
                String selectedCategory = CategoryBox.getValue();
                questionBank.lookupAll("*").forEach(node -> {
                    questionBank.getChildren().remove(node);
                });
                String query1 ="SELECT QUESTION_NAME, QUESTION_ID FROM QUESTION WHERE CATEGORY_ID IN" +
                        "(SELECT CATEGORY_ID FROM CATEGORY WHERE CATEGORY_NAME='"+selectedCategory+"')";
                Statement stm1 =null;
                try {
                    stm1 = conn.createStatement();
                    ResultSet rs1=stm1.executeQuery(query1);
                    while(rs1.next()){
                        String question_name = rs1.getNString("QUESTION_NAME");
                        int question_id = rs1.getInt("QUESTION_ID");
                        Pane question1 =createQuestionPane(question_name,question_id);
                        questionBank.setPrefSize(questionBank.getMaxWidth(),questionBank.getHeight()+30);
                        questionBank.getChildren().add(question1);
                        question1.setLayoutY(this.height);
                        this.height=this.height+30;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
