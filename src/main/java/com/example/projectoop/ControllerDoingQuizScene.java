package com.example.projectoop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class ControllerDoingQuizScene {
    private Stage stage;
    private Scene  scene;
    private Node anyNode;
    @FXML
    private Label timerLabel;
    @FXML
    private Label myLabel;
    @FXML
    private AnchorPane question;
    @FXML
    private AnchorPane navigation;

    private int countdownSeconds;

    public void setCountdownSeconds(int x){
        this.countdownSeconds=x;
    }
    public void initialize() throws IOException {
        int x=60;
        setCountdownSeconds(x);
        timerLabel.setText(formatTime(countdownSeconds));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            countdownSeconds--;
            if(countdownSeconds >= 0) {
                timerLabel.setText(formatTime(countdownSeconds));
            }
            else{
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FinishQuizScene.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) myLabel.getScene().getWindow();
                Scene scene1 = new Scene(root);
                stage.setScene(scene1);
                stage.show();
            }
        }));
        timeline.setCycleCount(x+1);
        timeline.play();

        Pane questionPane = createQuestionPane(1);
        questionPane.setLayoutY(10);
        questionPane.setLayoutX(100);
        Pane numberPane = createQuestionButton(1);
        numberPane.setLayoutX(20);
        numberPane.setLayoutY(50);
        Pane NumberPane = createQuestionNumberPane(1);
        NumberPane.setLayoutX(10);
        NumberPane.setLayoutY(10);
        question.getChildren().addAll(questionPane,NumberPane);
        navigation.getChildren().add(numberPane);
    }


    private String formatTime(int seconds) {
        int hours = seconds/3600;
        int minutes = seconds%3600/60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d",hours, minutes, secs);
    }
    public Pane createQuestionButton(int x) {
        Pane numberPane = new Pane();
        numberPane.setPrefSize(20, 40);
        numberPane.setStyle("-fx-border-color: #000000;\n" +
                "    -fx-border-width: 1 ;\n" +
                "    -fx-border-style: solid ;");


        Label number = new Label();
        number.setText("" + x);
        number.setLayoutY(0);
        number.setLayoutX(7);

        Button button = new Button();
        button.setPrefSize(18, 10);
        button.setStyle("-fx-background-color: grey");

        button.setLayoutY(14);
        button.setLayoutX(1);

        numberPane.getChildren().addAll(number, button);

        return numberPane;
    }
    public Pane createQuestionPane(int question_id){
        Pane question_Pane = new Pane();
        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query1 ="SELECT question_name FROM question WHERE question_id ="+question_id;
        String query2="select * from answer where question_id = " +question_id;
        Statement stm1 =null;
        Statement stm2=null;
        try {
            // Tạo kết nối
            Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            // Thực hiện các truy vấn SQL
            stm1 = conn.createStatement();
            ResultSet rs = stm1.executeQuery(query1);

            double h=0;
            while (rs.next()) {
                String question_name = rs.getNString("question_name");
                Label question = new Label(question_name);
                question.setPrefSize(550, 50);
                question.setWrapText(true);
                question.setLayoutX(20);
                question.setLayoutY(0);
                question_Pane.getChildren().add(question);
                h=h+50;
            }
            stm2 = conn.createStatement();
            ResultSet rs2 = stm2.executeQuery(query2);
            while (rs2.next()) {
                String answer_name = rs2.getNString("answer_name");
                CheckBox checkBox = new CheckBox();
                checkBox.setLayoutX(30);
                checkBox.setLayoutY(h);
                Label answer = new Label(answer_name);
                answer.setLayoutX(50);
                answer.setLayoutY(h);
                question_Pane.getChildren().addAll(answer, checkBox);
                h = h + 30;
            }
            question_Pane.setStyle("-fx-background-color: #E7F3F5");
            question_Pane.setPrefSize(600, h + 50);
            // Đóng kết nối
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question_Pane;
    }
    public Pane createQuestionNumberPane(int x){
        Pane questionNumberPane=new Pane();
        questionNumberPane.setPrefSize(75,100);
        Label question = new Label("Question");
        question.setLayoutX(5);
        question.setLayoutY(5);
        Label number = new Label(""+x);
        number.setLayoutY(5);
        number.setLayoutX(60);
        Label mark = new Label("Marked out of 1.00");
        mark.setPrefSize(60,50);
        mark.setWrapText(true);
        mark.setLayoutY(50);
        mark.setLayoutX(5);
        questionNumberPane.setStyle("-fx-border-color: #000000;\n" +
                "    -fx-border-width: 1 ;\n" +
                "    -fx-border-style: solid ;");

        questionNumberPane.getChildren().addAll(question,number, mark);
        return questionNumberPane;
    }
}


