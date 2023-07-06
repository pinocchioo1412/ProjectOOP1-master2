package com.example.projectoop;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.Node;

public class Controller1  implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources){
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
    }
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
    public void initialize() {
        List<String> labelValues = SaveQuiz.getLabelValues();
        for (String value : labelValues) {
            Button button = new Button(value);

            Image img = new Image("C:\\Users\\admin\\Desktop\\project here\\OOP project\\ProjectOOP1-master\\src\\main\\resources\\Image\\iconchuv.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(26);
            view.setFitWidth(21);
            view.setPreserveRatio(true);
            button.setStyle("-fx-background-color: #ffffff ");
//            button.setPrefHeight(263);
//            button.setPrefWidth(33);
            button.setGraphic(view);
            button.setOnAction(event -> {
                try {
                    switchToAttemptQuizScene(event);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Xử lý ngoại lệ theo nhu cầu của bạn
                }
            });

            Quizbox.getChildren().add(button); // Thêm button với 1 quiz vào quizbox nma k biết add action
        }
    }
}