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
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GiaoDien1.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            Controller1 controller = new Controller1();
            fxmlLoader.setController(controller);
            controller.initialize();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws SQLException {
        launch();

    }
}

