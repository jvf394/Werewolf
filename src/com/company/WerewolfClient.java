package com.company;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WerewolfClient extends Application {
    private static Socket wwSocket;
    private static PrintWriter out;
    private static BufferedReader in;


    public static void main(String[] args) throws IOException {
        //String hostName = "localhost";
        String hostName = "45.55.65.225";
        int portNumber = 1234;

        wwSocket = new Socket(hostName, portNumber);
        out = new PrintWriter(wwSocket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(wwSocket.getInputStream()));
        BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {


        GuiCreator wwgc = new GuiCreator();
        int num = Integer.parseInt(in.readLine()); //Player Number

        Player me = new Player(
                wwgc.usernameGui(num), //Username
                num, //Player Number
                -1, //Card
                -1, //Original Card
                null, //Socket
                -1 //Turn
        );


        //Sends username and number of players (host) to server
        out.println(me.getName());

        //Host sends character selection
        if (me.getNum() == 1) {
            in.readLine();
            out.println(wwgc.characterSelectionGui());
        }

        //Shows players their original card
        String tempString = in.readLine(); //original card and name
        me.setOrigCard(Integer.parseInt(tempString.split(",")[0]));
        me.setName(tempString.split(",")[1]);
        me.setCard(me.getOrigCard());
        System.out.println("Card and Name: " + me.getOrigCard() + " " + me.getName());
        System.out.println("me.Card: " + me.getCard());
        System.out.println("me.Name: " + me.getName());


        wwgc.showCardGui(me.getCard(), me.getName());


        //MAKE A GUI HERE!!!
        tempString = in.readLine();
        System.out.println(tempString);
        while (Integer.parseInt(tempString) != me.getCard() && Integer.parseInt(tempString) != -1) {
            System.out.println("Here2");
            tempString = in.readLine();
        }
        System.out.println("Here3");
        if (Integer.parseInt(tempString) > 5) {
            System.out.println("Here4");
            wwgc.takeTurnGui(Integer.parseInt(tempString));
            System.out.println(tempString + " That's me! (" + me.getName() + ")");
            out.println(tempString + " That's me! (" + me.getName() + ")");
        }
        Thread.sleep(3000);
        System.out.println("done");
        out.println("next");
        in.readLine();
        //System.out.println("test");
        System.exit(1);
    }
}
