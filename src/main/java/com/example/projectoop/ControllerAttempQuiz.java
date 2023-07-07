package com.example.projectoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ControllerAttempQuiz {
    private Stage stage;
    private Scene scene;
    @FXML
    private Label quizName;
    @FXML
    private Label timeLimit;
    public void initialize() throws SQLException {
        String url = "jdbc:sqlserver://" + "localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String user = "oop";
        String password = "123";
        Connection conn = DriverManager.getConnection(url, user, password);
        String sql = "select quiz_name,DATEPART(minute, time_limit)+60*DATEPART(hour, time_limit) as time_limit from quiz where quiz_id in (select quiz_id from quiz_in_progress)";
        Statement stm = null;
        stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            String QuizName = rs.getNString("quiz_name");
            quizName.setText(QuizName);
            int x = rs.getInt("time_limit");
            timeLimit.setText(x+" minutes");
        }
    }
    public void switchToCreateQuizScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateQuizScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToEdittingQuizScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EdittingQuizScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void startAttemp(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("StartAttempScene.fxml"));
        Scene secondaryScene = new Scene(root);
        stage.setScene(secondaryScene);

        stage.show();
    }

}
