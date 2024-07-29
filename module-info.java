module com.example.moviequotesapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens com.example.moviequotesapp to javafx.fxml;
    exports com.example.moviequotesapp;
}
