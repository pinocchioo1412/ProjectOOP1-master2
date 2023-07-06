package com.example.projectoop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class OOPApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GiaoDien1.fxml")));
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        launch();
        ReadTextFile abc= new ReadTextFile();
        abc.readfile();

    }

}