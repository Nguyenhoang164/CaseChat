module com.example.chatbytcp {
    requires javafx.controls;
    requires javafx.fxml;

requires java.sql;
    opens com.example.chatbytcp to javafx.fxml;
    exports com.example.chatbytcp;
}