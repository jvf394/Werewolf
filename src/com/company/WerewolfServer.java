package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class WerewolfServer {

    final static int PORTNUMBER = 1234;
    private static int totalPlayers, card, turn;
    private static ServerSocket serverSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static WerewolfProtocol wwp;
    private static String inputLine, deck;


    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(PORTNUMBER);
            wwp = new WerewolfProtocol();


            //Accepts host and stores username, #players
            acceptMainPlayer();
            System.out.println("finished accepting first player");

            //Accepts the rest of the players
            for (int i = 0; i < totalPlayers - 1; i++) {
                acceptPlayers();
            }
            System.out.println("finished accepting all players");

            //Brings up character selection for host, and waiting screen for other players
            wwp.sendMain("1", "0");

            //Receives shuffled deck
            deck = wwp.listenPlayer(3);
            //Deals cards to players
            wwp.dealCards(deck);
            wwp.sortPlayers();
            wwp.showCard();

            while(wwp.beginGame()!=9){
            }
            System.out.println("WE DID IT");
            System.out.println(in.readLine() + "checkpoint 2");


        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORTNUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void acceptMainPlayer() throws IOException {
        try {
            Socket clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("1");
            inputLine = in.readLine();
            wwp.playerList(inputLine, clientSocket);
            totalPlayers = wwp.getNumPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void acceptPlayers() throws IOException {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("player accepted");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("0");
            inputLine = in.readLine();
            wwp.playerList(inputLine, clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}