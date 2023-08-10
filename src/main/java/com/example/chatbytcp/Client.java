package com.example.chatbytcp;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        }catch (IOException e){
            System.out.println(e.getMessage());
            CloseEveryThing(socket,bufferedWriter,bufferedReader);
        }

    }
    public void sendMessageToClient(String messageFrom){
        try {
            bufferedWriter.write(messageFrom);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
            CloseEveryThing(socket,bufferedWriter,bufferedReader);
        }
    }
    public void receiveMessage(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String message = bufferedReader.readLine();
                        HelloController.addLabel(message,vBox);
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                        CloseEveryThing(socket,bufferedWriter,bufferedReader);
                        break;
                    }
                }

            }
        }).start();
    }

    public void CloseEveryThing(Socket socket , BufferedWriter bufferedWriter , BufferedReader bufferedReader){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
