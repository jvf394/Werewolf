package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class WerewolfProtocol {
    //Game State
    private static final int
            FIRSTPLAYER = 0,
            WAITING = 1,
            DOP = 2,
            SEE = 3,
            ROB = 4,
            TRO = 5,
            DRU = 6,
            INS = 7,
            END = 8,
            DONE = 9;

    //Card Enumeration
    private static final int
            DOPPELGANGER = 0,
            SEER = 6,
            ROBBER = 7,
            TROUBLEMAKER = 8,
            DRUNK = 9,
            INSOMNIAC = 10;

    //Game Contains?
    private static boolean
            doppelganger = false,
            seer = false,
            robber = false,
            troublemaker = false,
            drunk = false,
            insomniac = false;


    private static int
            doppelgangerPlayer = -1,
            seerPlayer = -1,
            robberPlayer = -1,
            troublemakerPlayer = -1,
            drunkPlayer = -1,
            insomniacPlayer = -1;
    private int state = FIRSTPLAYER, card, numberOfPlayers = 0, totalPlayers;
    private Player[] players, playOrder;
    public String Deck;

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
        playOrder=players;
        Arrays.sort(playOrder);
    }

    public void showCard() throws IOException {
        String turn;
        for (int i = 3; i < totalPlayers + 3; i++) {
            turn = Integer.toString(players[i].getTurn());
            if ((!turn.equals("0")) && !turn.equals("1") && !turn.equals("2")) {
                Socket skt = players[i].getConnection();
                PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                out.println(players[i].getName() + " " + players[i].getCard());
            }
        }

    }

    public int beginGame() throws InterruptedException, IOException {
        //NEEDS TO EXCLUDE MIDDLE PLAYERS!!!
        System.out.println(state);
        if (state == WAITING) {
            state = DOP;
            for (int i = 0; i < totalPlayers + 3; i++) {
                System.out.println(playOrder[i].getName() + ": " + playOrder[i].getCard());
            }
            for (int i = 0; i < totalPlayers + 3; i++) {
                card = playOrder[i].getCard();
                System.out.println(playOrder[i].getCard());

                if (playOrder[i].getTurn() > 2) {
                    if (card == DOPPELGANGER) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is doppelganger");
                        doppelgangerPlayer = i;
                        doppelganger = true;
                    } else if (card == 1 || card == 2) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is werewolf");
                    } else if (card == 3) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is minion");
                    } else if (card == 4 || card == 5) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is mason");
                    } else if (card == SEER) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is seer");
                        seerPlayer = i;
                        seer = true;
                    } else if (card == ROBBER) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is robber");
                        robberPlayer = i;
                        robber = true;
                    } else if (card == TROUBLEMAKER) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is troublemaker");
                        troublemakerPlayer = i;
                        troublemaker = true;
                    } else if (card == DRUNK) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is drunk");
                        drunkPlayer = i;
                        drunk = true;
                    } else if (card == INSOMNIAC) {
                        System.out.println("Player #" + playOrder[i].getTurn() + ": " + playOrder[i].getName() + " is insomniac");
                        insomniacPlayer = i;
                        insomniac = true;
                    }
                }
            }
            System.out.println("Time for turns!");
            beginGame();
        } else if (state == DOP) {
            state = SEE;
            System.out.println("Doppelganger's turn");
            if (doppelganger) {
                System.out.println("go doppelganger");
                for (int i = 0; i < totalPlayers + 3; i++) {
                    String c;
                    c = Integer.toString(players[i].getTurn());
                    if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                        Socket skt = players[i].getConnection();
                        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                            /*System.*/
                        out.println("0");
                    }
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(playOrder[doppelgangerPlayer].getConnection().getInputStream()));
                System.out.println(in.readLine());
            } else {
                System.out.println("No Doppelganger");
                beginGame();
            }
        } else if (state == SEE) {
            state = ROB;
            System.out.println("Seer's turn");
            if (seer) {
                for (int i = 0; i < totalPlayers + 3; i++) {
                    String c;
                    c = Integer.toString(players[i].getTurn());
                    if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                        Socket skt = players[i].getConnection();
                        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                            /*System.*/
                        out.println("6");
                    }
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(playOrder[seerPlayer].getConnection().getInputStream()));
                System.out.println(in.readLine());

            } else {
                System.out.println("No Seer");
                beginGame();
            }
        } else if (state == ROB) {
            state = TRO;
            System.out.println("Robber's turn");
            if (robber) {
                for (int i = 0; i < totalPlayers + 3; i++) {
                    String c;
                    c = Integer.toString(players[i].getTurn());
                    if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                        Socket skt = players[i].getConnection();
                        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                            /*System.*/
                        out.println("7");
                    }
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(playOrder[robberPlayer].getConnection().getInputStream()));
                System.out.println(in.readLine());
            } else {
                System.out.println("No Robber");
                beginGame();
            }
        } else if (state == TRO) {
            state = DRU;
            System.out.println("Troublemaker's turn");
            if (troublemaker) {
                for (int i = 0; i < totalPlayers + 3; i++) {
                    String c;
                    c = Integer.toString(players[i].getTurn());
                    if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                        Socket skt = players[i].getConnection();
                        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                            /*System.*/
                        out.println("8");
                    }
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(playOrder[troublemakerPlayer].getConnection().getInputStream()));
                System.out.println(in.readLine());
            } else {
                System.out.println("No Troublemaker");
                beginGame();
            }
        } else if (state == DRU) {
            state = INS;
            System.out.println("Drunk's turn");
            if (drunk) {
                for (int i = 0; i < totalPlayers + 3; i++) {
                    String c;
                    c = Integer.toString(players[i].getTurn());
                    if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                        Socket skt = players[i].getConnection();
                        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                            /*System.*/
                        out.println("9");
                    }
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(playOrder[drunkPlayer].getConnection().getInputStream()));
                System.out.println(in.readLine());
            } else {
                System.out.println("No Drunk");
                beginGame();
            }
        } else if (state == INS) {
            state = END;
            System.out.println("Insomniac's turn");
            if (insomniac) {
                for (int i = 0; i < totalPlayers + 3; i++) {
                    String c;
                    c = Integer.toString(players[i].getTurn());
                    if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                        Socket skt = players[i].getConnection();
                        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                            /*System.*/
                        out.println("10");
                    }
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(playOrder[insomniacPlayer].getConnection().getInputStream()));
                System.out.println(in.readLine());
            } else {
                System.out.println("No Insomniac");
                beginGame();
            }
        } else if (state == END) {
            state = DONE;
            for (int i = 0; i < totalPlayers + 3; i++) {
                String c;
                c = Integer.toString(players[i].getTurn());
                if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                    Socket skt = players[i].getConnection();
                    PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
                        /*System.*/
                    out.println("-1");
                }
            }
        }
        return state;
    }
}