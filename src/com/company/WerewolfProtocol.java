package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class WerewolfProtocol {
    //Game State
    private static final int
            FIRSTPLAYER = 0,
            WAITING = 1;

    public String Deck;
    private int state = FIRSTPLAYER, card, numberOfPlayers = 0, totalPlayers;
    private Player[] players, playOrder;

    public String playerList(String theInput, Socket socket, int playerNumber) {
        if (state == FIRSTPLAYER) {
            totalPlayers = Integer.parseInt(theInput.split(",")[1]);
            theInput = theInput.substring(0, theInput.lastIndexOf(","));
            players = new Player[totalPlayers + 3];
            state = WAITING;
        }

        players[numberOfPlayers + 3] = new Player(theInput, playerNumber, -1, -1, socket, numberOfPlayers + 3);
        numberOfPlayers++;

        return Integer.toString(numberOfPlayers - 1);
    }

    public int getNumPlayers() {
        return totalPlayers;
    }

    public void characterSelect() throws IOException {
        Socket skt = players[3].getConnection();
        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
        out.println();
    }

    public String listenPlayer(int playerNum) throws IOException {
        Socket clientSocket = players[playerNum].getConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return in.readLine();
    }

    public void dealCards(String deck) {
        players[0] = new Player("Center-One", -1, -1, -1, null, 0);
        players[1] = new Player("Center-Two", -1, -1, -1, null, 1);
        players[2] = new Player("Center-Three", -1, -1, -1, null, 2);
        Deck = deck;
        for (int i = 0; i < totalPlayers + 3; i++) {
            players[i].setCard(Integer.parseInt(deck.split(",")[i]));
            System.out.println(players[i].getName() + ": " + players[i].getCard());
        }
        playOrder = players;
        Arrays.sort(playOrder);
    }

    public void showCard() throws IOException {
        String turn;
        for (int i = 0; i < totalPlayers + 3; i++) {
            turn = Integer.toString(players[i].getTurn());
            if ((!turn.equals("0")) && !turn.equals("1") && !turn.equals("2")) {
                Socket skt = players[i].getConnection();
                PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                System.out.println("sendin da card "+players[i].getCard()+" to "+players[i].getName());
                out.println(players[i].getCard());
                System.out.println("They got it!!");
            }
        }

    }

    public void beginGame() throws InterruptedException, IOException {

        boolean runSecondWolf = true;
        boolean runSecondMason = true;

        for (int i = 0; i < numberOfPlayers+3; i++) {
            for (Player player : playOrder) {
                if (!isCenterCard(player) && player.getOrigCard() == i) {
                    switch (i) {
                        case 1:
                            System.out.println("werewolf turn");
                            PlayerActions.wereWolf(player, playOrder, this);
                            runSecondWolf = false;
                            break;
                        case 2:
                            System.out.println("2 turn");
                            if (runSecondWolf) {
                                PlayerActions.wereWolf(player, playOrder, this);
                            }
                            break;
                        case 3:
                            System.out.println("3 turn");
                            PlayerActions.minion(player, playOrder, this);
                            break;
                        case 4:
                            System.out.println("4 turn");
                            PlayerActions.mason(player, playOrder, this);
                            break;
                        case 6:
                            System.out.println("6 turn");
                            PlayerActions.seer(player, playOrder, this);
                            break;
                        case 7:
                            System.out.println("7 turn");
                            PlayerActions.robber(player, playOrder, this);
                            break;
                        case 8:
                            System.out.println("8 turn");
                            PlayerActions.troubleMaker(player, playOrder, this);
                            break;
                    }
                }
            }
        }

    }

    public boolean isCenterCard(Player possibleCenter) {
        return (possibleCenter.getTurn() == 0 ||
                possibleCenter.getTurn() == 1 ||
                possibleCenter.getTurn() == 2);
    }

    public void tellEveryone(String msg) {
        tellEveryone(msg, "null");
    }

    public void tellEveryone(String msg, String mod) {
        String c;
        for (int i = 0; i < totalPlayers + 3; i++) {
            c = Integer.toString(players[i].getTurn());
            if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                Socket skt = players[i].getConnection();
                PrintWriter out = null;
                try {
                    out = new PrintWriter(skt.getOutputStream(), true);
                    out.println(msg + "," + mod);
                } catch (IOException e) {
                    System.out.println("Something went wrong in the tell everyone method!");
                    e.printStackTrace();
                }
            }
        }
    }

}