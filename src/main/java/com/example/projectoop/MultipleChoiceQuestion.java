package com.example.projectoop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;


public class MultipleChoiceQuestion {
    private Label question;
    private ToggleGroup toggleGroup;
    private Pane question_Pane;

    private double h;

    public MultipleChoiceQuestion(String question_name) {
        question = new Label(question_name);
        toggleGroup = new ToggleGroup();
        question_Pane = new Pane();
        question.setPrefSize(550, 100);
        question.setWrapText(true);
        question.setLayoutX(20);
        question.setLayoutY(10);
        question.setAlignment(Pos.TOP_LEFT);
        question_Pane.getChildren().add(question);
        h=h+100;
    }

    public void addOption(String answer) {
        RadioButton option = new RadioButton(answer);
        option.setToggleGroup(toggleGroup);
        option.setToggleGroup(toggleGroup);
        option.setLayoutX(30);
        option.setLayoutY(h);
        option.setWrapText(true);
        option.setPrefWidth(400);
        question_Pane.getChildren().add(option);
        h = h + 60;
    }

    public Label getQuestion() {
        return question;
    }

    public Pane getQuestion_Pane() {
        return question_Pane;
    }
    public void setQuestionPane(){
        question_Pane.setStyle("-fx-background-color: #E7F3F5");
        question_Pane.setPrefSize(600, h + 50);
    }
    public String getSelectedAnswer() {
        RadioButton selectedOption = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedOption != null) {
            return selectedOption.getText();
        }
        return "Chưa chọn";
    }
}
