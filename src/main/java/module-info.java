module com.example.projectoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projectoop to javafx.fxml;
    exports com.example.projectoop;
}