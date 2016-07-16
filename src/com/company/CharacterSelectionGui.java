package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Fedor on 11/28/2015.
 */
public class CharacterSelectionGui  extends Application {
    Socket wwSocket;
    PrintWriter out;
    String deckStr = null;
    public void setSocket(Socket wwSocket){
        try {
            out = new PrintWriter(wwSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
    launch(args);
    }
    @Override
    public void start(Stage characterOptions) throws Exception {

}}
