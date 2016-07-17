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
        String[] gameInfo = wwgc.usernameGui(num);

        Player me = new Player(
                gameInfo[0], //Username
                num, //Player Number
                -1, //Card
                -1, //Original Card
                null, //Socket
                -1 //Turn
        );


        //Sends username and number of players (host) to server
        out.println(gameInfo[0] + "," + gameInfo[1]);

        //Host sends character selection
        if (me.getNum() == 1) {
            in.readLine();
            out.println(wwgc.characterSelectionGui());
        }

        //Shows players their original card
        me.setOrigCard(Integer.parseInt(in.readLine()));//Receives Original Card
        //Makes both werewolves respond to a 1
        if (me.getOrigCard() == 2) {
            me.setOrigCard(1);
        }
        //Makes both masons respond to a 4
        if (me.getOrigCard() == 5) {
            me.setOrigCard(4);
        }
        //Makes all villagers respond to a 13
        if (me.getOrigCard() == 14 || me.getOrigCard() == 15) {
            me.setOrigCard(13);
        }
        me.setCard(me.getOrigCard());
        System.out.println(me.toString());


        wwgc.showCardGui(me.getOrigCard(), me.getName() + ", you start as:");


        //MAKE A GUI HERE!!!
        while (Integer.parseInt(in.readLine().split(":")[0]) != me.getOrigCard()) {
            System.out.println("someone's turn received");
        }
        System.out.println("lets a go");
        switch (me.getOrigCard()) {
            case 0: //Doppelganger
                System.out.println("my turn");
                break;
            case 1: //Werewolf
                String werewolfIn = new String(in.readLine());
                System.out.println("my turn");
                out.println(wwgc.takeTurnGui(werewolfIn, me));
                if (werewolfIn.contains("none")) {
                    wwgc.showCardGui(Integer.parseInt(in.readLine().split(":")[0]), "That middle card is:");
                }
                break;
            case 3: //Minion
                System.out.println("my turn");
                wwgc.takeTurnGui(in.readLine(), me);
                break;
            case 4: //Mason
                System.out.println("my turn");
                break;
            case 6: //Seer
                System.out.println("my turn");
                break;
            case 7: //Robber
                System.out.println("my turn");
                break;
            case 8: //Troublemaker
                System.out.println("my turn");
                break;
            case 9: //Drunk
                System.out.println("my turn");
                break;
            case 10: //Insomniac
                System.out.println("my turn");
                break;
            case 11: //Hunter
                System.out.println("my turn");
                break;
            case 12: //Tanner
                System.out.println("my turn");
                break;
            case 13: //Villager
                System.out.println("my turn");
                break;
        }
        // wwgc.takeTurnGui(me.getOrigCard()) ;
        System.exit(1);
    }
}
