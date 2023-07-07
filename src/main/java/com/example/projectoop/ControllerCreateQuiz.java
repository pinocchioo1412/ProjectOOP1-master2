package com.example.projectoop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerCreateQuiz {
    private ObservableList<String> Days = FXCollections.observableArrayList("1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
    private ObservableList<String> Months = FXCollections.observableArrayList("January", "Februarry", "March", "April", "May", "June", "July", "August", "September", "Octorber", "November", "December");
    private ObservableList<String> Years = FXCollections.observableArrayList("2023", "2024", "2025", "2026", "2027");
    private ObservableList<String> Minutes = FXCollections.observableArrayList("0","1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33", "34", "35", "36", "37","38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60");
    private ObservableList<String> Hours = FXCollections.observableArrayList("0","1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
    private ObservableList<String> Typeoftimes = FXCollections.observableArrayList("minute", "hours");
    private ObservableList<String> Whenthetimeexpires = FXCollections.observableArrayList("Open attempts are submitted automatically");

    @FXML
    ComboBox<String> days, days1;
    @FXML
    ComboBox<String> months, months1;
    @FXML
    ComboBox<String> years, years1;
    @FXML
    ComboBox<String> minutes, minutes1;
    @FXML
    ComboBox<String> hours, hours1;
    @FXML
    ComboBox<String> typeoftime;
    @FXML
    ComboBox<String> whenthetimeexpires;
    Stage stage;
    Scene scene;
    @FXML
    TextField quizname;
    @FXML
    private TextField timenumber;
    @FXML
    private ImageView namewarning;
    @FXML
    private HBox openthequiz;
    @FXML
    private HBox closethequiz;
    @FXML
    private CheckBox enable1;
    @FXML
    private CheckBox enable2;
    @FXML
    private CheckBox enable3;
    @FXML
    private Button createQUIZ;
    public void setDays(){
        this.days.setItems(Days);
        this.days.setValue("1");
        this.days1.setItems(Days);
        this.days1.setValue("1");
    }
    public void setMonths(){
        this.months.setItems(Months);
        this.months.setValue("January");
        this.months1.setItems(Months);
        this.months1.setValue("January");
    }
    public void setYears(){
        this.years.setItems(Years);
        this.years.setValue("2023");
        this.years1.setItems(Years);
        this.years1.setValue("2023");
    }
    public void setMinutes(){
        this.minutes.setItems(Minutes);
        this.minutes.setValue("0");
        this.minutes1.setItems(Minutes);
        this.minutes1.setValue("0");
    }
    public void setHours(){
        this.hours.setItems(Hours);
        this.hours.setValue("0");
        this.hours1.setItems(Hours);
        this.hours1.setValue("0");
    }
    public void setTypeoftimes(){
        this.typeoftime.setItems(Typeoftimes);
        this.typeoftime.setValue("minute");
    }
    public void setWhenthetimeexpires(){
        this.whenthetimeexpires.setItems(Whenthetimeexpires);
        this.whenthetimeexpires.setValue("Open attempts are submitted automatically");
    }
    public void initialize(){
        setDays();
        setMonths();
        setYears();
        setMinutes();
        setHours();
        setTypeoftimes();
        setWhenthetimeexpires();
        quizname.textProperty().addListener((Observable, oldvalue, newValue) -> {
            if (newValue.isEmpty()) {
                namewarning.setVisible(true);
            } else {
                namewarning.setVisible(false);
            }
        });

    }
//    public void Create (ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("GiaoDien1.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
    public void cancelClickEvent (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GiaoDien1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void openquiz(ActionEvent event) throws IOException{
        if(enable1.isSelected()){
           days.setDisable(false);
           months.setDisable(false);
           years.setDisable(false);
           hours.setDisable(false);
           minutes.setDisable(false);
        }
    }
    public void closequiz(ActionEvent event) throws IOException{
        if(enable2.isSelected()){
            days1.setDisable(false);
            months1.setDisable(false);
            years1.setDisable(false);
            hours1.setDisable(false);
            minutes1.setDisable(false);
        }
    }
    @FXML
    void create(ActionEvent event) {
        try {
            Stage ag0r1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GiaoDien1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ag0r1.setScene(scene);
            ag0r1.show();
            Controller1 controller = loader.getController();
            SaveQuiz.addLabelValue(quizname.getText());
            controller.initialize();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void savequiz(ActionEvent event) throws IOException{
            try {
                String DB_URL = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
                String DB_USER = "oop";
                String DB_PASSWORD = "123";
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String maxQuizIdQuery = "SELECT MAX(QUIZ_ID) FROM QUIZ";
                PreparedStatement maxQuizIdStatement = connection.prepareStatement(maxQuizIdQuery);
                ResultSet resultSet = maxQuizIdStatement.executeQuery();
                int maxQuizId = 100; // cho QUIZ_ID ban đầu là 100
                if (resultSet.next()) {
                    int currentMaxQuizId = resultSet.getInt(1);
                    maxQuizId = currentMaxQuizId + 1; // Tăng QUIZ_ID lên 1
                }

                String insertQuizQuery = "INSERT INTO QUIZ (QUIZ_ID, QUIZ_NAME, TIME_LIMIT) VALUES (?, ?, ?)";
                PreparedStatement quizStatement = connection.prepareStatement(insertQuizQuery);
                quizStatement.setInt(1, maxQuizId);
                quizStatement.setString(2, quizname.getText());
                quizStatement.setString(3, timenumber.getText());
                quizStatement.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
