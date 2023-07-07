package com.example.projectoop;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ControllerFinishQuizScene {
    private Stage stage;
    private Scene scene;

    @FXML
    private AnchorPane question;
    @FXML
    private AnchorPane navigation;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label timeTaken;
    @FXML
    private Label mark;
    @FXML
    private Label grade;

    public ArrayList<MultipleChoiceQuestion> questions= new ArrayList<MultipleChoiceQuestion>();

    public void initialize() throws SQLException {
        String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String USER_NAME = "oop";
        String PASSWORD = "123";
        String query1 ="SELECT question_id FROM question WHERE QUESTION_ID IN (SELECT QUESTION_ID FROM QUIZ_QUESTION WHERE QUIZ_ID IN (SELECT QUIZ_ID FROM QUIZ_IN_PROGRESS))";
        String query2 = "Select CONVERT(NVARCHAR, START_TIME, 120) as start_time, CONVERT(NVARCHAR, END_TIME, 120) as end_time, CONVERT(VARCHAR, DATEDIFF(second,START_TIME,END_TIME), 120) AS time_taken, GRADE from QUIZ_IN_PROGRESS";
        Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement stm2 =null;
        Statement stm1 =null;

        stm1 = conn.createStatement();
        ResultSet rs = stm1.executeQuery(query1);
        int i=1;
        double h=150;
        while (rs.next()) {
            int question_id = rs.getInt("question_id");
            Pane questionPane = createQuestionPane(question_id);
            questionPane.setLayoutY(h);
            questionPane.setLayoutX(100);
            Pane NumberPane = createQuestionNumberPane(i);
            NumberPane.setLayoutX(10);
            NumberPane.setLayoutY(h);
            Pane numberPane = createQuestionButton(i);
            numberPane.setLayoutX(20 + (i - 1) * 30);
            numberPane.setLayoutY(50 + ((i - 1) / 5) * 50);
            question.getChildren().addAll(questionPane, NumberPane);

            Pane answerPane=new Pane();
            answerPane.setStyle("-fx-background-color: yellow");
            answerPane.setPrefSize(600, 30);
            Label answer = new Label();
            int answer_id = questions.get(i-1).getQuestion_id();
            String sql = "select answer_text from answer where answer_grade > 0 and question_id =" + answer_id;
            Statement stm = null;
            stm = conn.createStatement();
            ResultSet rs3 = stm.executeQuery(sql);
            while (rs3.next()) {
                String rightAnswer = rs3.getNString("answer_text");
                answer.setText("The correct answer is: "+rightAnswer);
            }
            answer.setLayoutX(20);
            answer.setLayoutY(5);
            answerPane.getChildren().add(answer);
            answerPane.setLayoutY(h+questionPane.getPrefHeight()+20);
            answerPane.setLayoutX(100);
            question.getChildren().add(answerPane);
            question.setPrefSize(question.getPrefWidth(), question.getPrefHeight() + 70 + questionPane.getPrefHeight());
            navigation.getChildren().add(numberPane);
            i++;
            h = h + 70 + questionPane.getPrefHeight();
        }
        stm2 = conn.createStatement();
        ResultSet rs2=stm2.executeQuery(query2);
        while (rs2.next()){
            String StartTime= rs2.getNString("start_time");
            startTime.setText(StartTime);
            String EndTime=rs2.getNString("end_time");
            endTime.setText(EndTime);
            int TimeTake = rs2.getInt("time_taken");
            timeTaken.setText(TimeTake/60+" minutes "+TimeTake%60 +" seconds");
            int Grade = rs2.getInt("grade");
            mark.setText(Grade+"/"+(i-1));
            float result = (float) Grade / (i-1);
            grade.setText(""+result);
        }
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

        button.setLayoutY(13);
        button.setLayoutX(1);

        numberPane.getChildren().addAll(number, button);

        return numberPane;
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
}
