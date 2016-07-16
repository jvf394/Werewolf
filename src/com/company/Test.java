package com.company;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test extends Application {
    private static String fromServer, fromUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text txt = new Text();
        TextField txtf = new TextField("field");
        Button knockKnockBtn = new Button("Who's There?");
        knockKnockBtn.setOnAction(event -> {
            out.println("Who's there?");
            try {
                txt.setText("Server: " + in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.show();
            fromUser = null;
        });
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            out.println(txtf.getText());
            try {
                txt.setText("Server: " + in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            fromUser = null;
        });
        txt.minWidth(1800);
        grid.add(txt, 0, 0);
        grid.add(knockKnockBtn, 1, 0);
        grid.add(txtf, 0, 1);
        grid.add(submitBtn, 1, 1);

        Scene scene = new Scene(grid,600,550);
        primaryStage.setScene(scene);
        txt.setText("Server: " + fromServer);
        primaryStage.show();
    }

    public static Socket wwSocket;
    public static PrintWriter out;
    public static BufferedReader in;

    public static void main(String[] args) throws IOException {
        launch(args);
        System.exit(1);
    }


}