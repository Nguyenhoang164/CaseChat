package com.example.chatbytcp;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_message;
    @FXML
    private ScrollPane sp_main;

    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       try {
           server = new Server(new ServerSocket(1234));
       }catch (IOException e){
           e.printStackTrace();
           System.out.println("error");
       }
       vbox_message.heightProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
               sp_main.setVvalue((Double) newValue);
           }
       });
       server.receiveMessage(vbox_message);
       button_send.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               String messageFrom = tf_message.getText();
               if (!messageFrom.isEmpty()){
                   HBox hBox = new HBox();
                   hBox.setAlignment(Pos.CENTER_RIGHT);
                   hBox.setPadding(new Insets(10,10,10,15));

                   Text text = new Text(messageFrom);
                   // chuoi ki tu tu dong xuong dong neu vuot qua gioi han
                   TextFlow textFlow = new TextFlow(text);
                   textFlow.setStyle("-fx-color: rgb(239,242,255);" + "-fx-background-color: rgb(15,125,242);" + "-fx-background-radius: 20px;");
                   text.setFill(Color.color(0.934,0.945,0.946));

                   hBox.getChildren().add(textFlow);
                   vbox_message.getChildren().add(hBox);
                   server.sendMessageToClient(messageFrom);
                   tf_message.clear();

               }
           }
       });

    }
    public static void addLabel(String messageFrom , VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(10,10,10,15));
        Text text = new Text(messageFrom);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" + "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(10,15,10,15));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
              vBox.getChildren().add(hBox);
            }
        });
    }

}