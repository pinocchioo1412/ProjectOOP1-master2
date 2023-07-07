package com.example.projectoop;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.image.Image;
public class Controller1  {
    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    private Stage stage;
    private Scene scene;

    private Parent root;
    @FXML
    private VBox Quizbox;


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
    public void switchToAttemptQuizScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AttempQuizScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize(){
        slider.setTranslateY(-200);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToY(0);
            slide.play();

            slider.setTranslateY(-200);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToY(-200);
            slide.play();

            slider.setTranslateY(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query ="SELECT quiz_name, quiz_id FROM quiz";
        Statement stm =null;
        try {
            // Tạo kết nối
            Connection conn = DriverManager.getConnection(DB_URL,USER_NAME, PASSWORD);
            // Thực hiện các truy vấn SQL
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()){
                String quiz_name=rs.getNString("quiz_name");
                final int quiz_id=rs.getInt("quiz_id");
                Button button = new Button(quiz_name);
                ImageView imageView = new ImageView(new Image("file:src/main/resources/Image/iconchuv.png"));
                imageView.setFitHeight(21);
                imageView.setFitWidth(26);
                button.setGraphic(imageView);
                button.setStyle("-fx-background-color: white");
                button.setOnAction(event -> {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("AttempQuizScene.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    String url = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
                    String user = "oop";
                    String password = "123";
                    String sql = "INSERT INTO quiz_in_progress (quiz_progress_id, quiz_id, start_time, end_time) VALUES ("+quiz_id+ ","+quiz_id+",null,null)";
                    // Tạo prepared statement để thực thi câu truy vấn
                    try {
                        Connection connection = DriverManager.getConnection(url,user,password);
                        Statement stm1 = null;
                        stm1=connection.createStatement();
                        stm1.executeQuery(sql);
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                Quizbox.getChildren().add(button);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}