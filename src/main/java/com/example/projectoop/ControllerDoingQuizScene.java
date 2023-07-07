package com.example.projectoop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.security.PublicKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.paint.Color;
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

    @FXML
    private Button finish;

    public ArrayList<MultipleChoiceQuestion>  questions= new ArrayList<MultipleChoiceQuestion>();

    public List<Integer> choice_answer= new ArrayList<>() ;

    private int countdownSeconds;

    private int grade=0;
    public void setCountdownSeconds(int x){
        this.countdownSeconds=x;
    }

    public void initialize() throws IOException, SQLException {
        String url1 = "jdbc:sqlserver://" + "localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String user1 = "oop";
        String password1 = "123";
        Connection conn1= DriverManager.getConnection(url1, user1, password1);
        String sql1 = "select quiz_name,DATEPART(minute, time_limit)+60*DATEPART(hour, time_limit) as time_limit from quiz where quiz_id in (select quiz_id from quiz_in_progress)";
        Statement stm4 = null;
        stm4 = conn1.createStatement();
        ResultSet rs4 = stm4.executeQuery(sql1);
        while (rs4.next()) {
            int n = rs4.getInt("time_limit");
            setCountdownSeconds(n*60);
        }
        conn1.close();
        int x=this.countdownSeconds;
        timerLabel.setText(formatTime(countdownSeconds));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            countdownSeconds--;
            if(countdownSeconds >= 0) {
                timerLabel.setText(formatTime(countdownSeconds));
            }
            else{
                Parent root = null;
                MultipleChoiceQuestion question1;
                questions.size();
                String url = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
                String user = "oop";
                String password = "123";
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(url,user,password);
                    for (int i = 0; i < questions.size(); i++) {
                        String answer = questions.get(i).getSelectedAnswer();
                        int answer_id = questions.get(i).getQuestion_id();
                        String sql = "select answer_grade from answer where answer_text like '" + answer + "'and question_id =" + answer_id;
                        Statement stm1 = null;
                        stm1 = connection.createStatement();
                        ResultSet rs = stm1.executeQuery(sql);
                        while (rs.next()) {
                            int n = rs.getInt("answer_grade");
                            if (n > 0) {
                                this.grade++;
                            }
                        }
                    }
                    System.out.println(grade);
                    connection.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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

        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query1 ="SELECT question_id FROM question WHERE QUESTION_ID IN (SELECT QUESTION_ID FROM QUIZ_QUESTION WHERE QUIZ_ID IN (SELECT QUIZ_ID FROM QUIZ_IN_PROGRESS))";
        Statement stm1 =null;
        Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        stm1 = conn.createStatement();
        ResultSet rs = stm1.executeQuery(query1);
        int i=1;
        double h=0;
        while (rs.next()) {
            int question_id=rs.getInt("question_id");
            Pane questionPane = createQuestionPane(question_id);
            questionPane.setLayoutY(h);
            questionPane.setLayoutX(100);
            Pane NumberPane = createQuestionNumberPane(i);
            NumberPane.setLayoutX(10);
            NumberPane.setLayoutY(h);
            Pane numberPane = createQuestionButton(i);
            numberPane.setLayoutX(20+(i-1)*30);
            numberPane.setLayoutY(50+((i-1)/5)*50);
            question.getChildren().addAll(questionPane, NumberPane);
            question.setPrefSize(question.getPrefWidth(),question.getPrefHeight()+20+questionPane.getPrefHeight());
            navigation.getChildren().add(numberPane);
            finish.setLayoutY(150+((i-1)/5)*50);
            i++;
            h=h+20+questionPane.getPrefHeight();
        }
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
        button.setStyle("-fx-background-color: white");

        button.setLayoutY(13);
        button.setLayoutX(1);

        numberPane.getChildren().addAll(number, button);

        return numberPane;
    }
    public Pane createQuestionPane(int question_id) throws SQLException{
        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query1 ="SELECT QUESTION_NAME FROM question WHERE QUESTION_ID ="+question_id;
        String query2="select * from answer where QUESTION_ID = " +question_id;
        Statement stm1 =null;
        Statement stm2=null;
            // Tạo kết nối
            Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            // Thực hiện các truy vấn SQL
            stm1 = conn.createStatement();
            ResultSet rs = stm1.executeQuery(query1);
            MultipleChoiceQuestion question = null;
            while (rs.next()) {
                String question_name = rs.getNString("question_name");
                question = new MultipleChoiceQuestion(question_name,question_id);
                stm2 = conn.createStatement();
                ResultSet rs2 = stm2.executeQuery(query2);
                while (rs2.next()) {
                    String answer_name = rs2.getNString("answer_text");
                    question.addOption(answer_name);
                }
                question.setQuestionPane();

            }
            // Đóng kết nối
            conn.close();
            questions.add(question);
        return question.getQuestion_Pane();
    }
    public Pane createQuestionNumberPane(int x){
        Pane questionNumberPane=new Pane();
        questionNumberPane.setPrefSize(75,100);
        Label question = new Label("Question");
        question.setTextFill(Color.RED);
        question.setLayoutX(5);
        question.setLayoutY(5);
        Label number = new Label(""+x);
        number.setLayoutY(5);
        number.setLayoutX(60);
        Label text = new Label("Not yet answered");
        text.setPrefSize(60,50);
        text.setWrapText(true);
        text.setLayoutY(15);
        text.setLayoutX(5);
        Label mark = new Label("Marked out of 1.00");
        mark.setPrefSize(60,50);
        mark.setWrapText(true);
        mark.setLayoutY(50);
        mark.setLayoutX(5);
        questionNumberPane.setStyle("-fx-border-color:  #1E90FF;\n" +
                "    -fx-border-width: 1 ;\n" +
                "    -fx-border-style: solid ;");

        questionNumberPane.getChildren().addAll(question,number, mark, text);
        return questionNumberPane;
    }
    public void setFinish(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nộp Bài");
        alert.setHeaderText("Finish");
        alert.setContentText("Do you finish thí quiz");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()== buttonTypeYes) {
            MultipleChoiceQuestion question1;
            questions.size();
            String url = "jdbc:sqlserver://" + "localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
            String user = "oop";
            String password = "123";
            Connection connection = DriverManager.getConnection(url, user, password);
            for (int i = 0; i < questions.size(); i++) {
                String answer = questions.get(i).getSelectedAnswer();
                int question_id = questions.get(i).getQuestion_id();
                String sql = "select answer_grade from answer where answer_text like N'" + answer + "'and question_id =" + question_id;
                Statement stm1 = null;
                stm1 = connection.createStatement();
                ResultSet rs = stm1.executeQuery(sql);
                while (rs.next()) {
                    int x = rs.getInt("answer_grade");
                    if (x > 0) {
                        this.grade++;
                        System.out.println("1");
                    }
                }

            }
            String sql = "update quiz_in_progress set grade ="+this.grade+", end_time=CURRENT_TIMESTAMP";
            Statement stm2= null;
            stm2 = connection.createStatement();
            stm2.executeUpdate(sql);
            connection.close();
            Parent root;
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
        else
            System.out.println("Code for cancel");

    }
}


