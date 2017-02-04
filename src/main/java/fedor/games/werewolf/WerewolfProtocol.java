package fedor.games.werewolf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

class WerewolfProtocol {
    //Game State
    private static final int
			FIRST_PLAYER = 0,
            WAITING = 1;

    private int state = FIRST_PLAYER;
	private int numberOfPlayers = 0;
	private int totalPlayers;
    private Player[] players, playOrder;

    void playerList(String theInput, Socket socket, int playerNumber) {
        if (state == FIRST_PLAYER) {
            totalPlayers = Integer.parseInt(theInput.split(",")[1]);
            players = new Player[totalPlayers + 3];
            state = WAITING;
        }

        players[numberOfPlayers + 3] = new Player(theInput.substring(0, theInput.lastIndexOf(",")), playerNumber, -1, -1, socket, numberOfPlayers + 3);
        numberOfPlayers++;

//        return Integer.toString(numberOfPlayers - 1);
    }

    int getNumPlayers() {
        return totalPlayers;
    }

    void characterSelect() throws IOException {
        Socket skt = players[3].getConnection();
        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
        out.println();
    }

    String listenPlayer(int playerNum) throws IOException {
        Socket clientSocket = players[playerNum].getConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return in.readLine();
    }

    void dealCards(String deck) {
        players[0] = new Player("Center-One", -1, -1, -1, null, 0);
        players[1] = new Player("Center-Two", -1, -1, -1, null, 1);
        players[2] = new Player("Center-Three", -1, -1, -1, null, 2);
        for (int i = 0; i < totalPlayers + 3; i++) {
            players[i].setCard(Integer.parseInt(deck.split(",")[i]));
            players[i].setOrigCard(players[i].getCard());
            System.out.println(players[i].getName() + ": " + players[i].getCard());
        }
        playOrder = players;
        Arrays.sort(playOrder);
    }

    void showCard() throws IOException {
        String turn;
        for (int i = 0; i < totalPlayers + 3; i++) {
            turn = Integer.toString(players[i].getTurn());
            if ((!turn.equals("0")) && !turn.equals("1") && !turn.equals("2")) {
                Socket skt = players[i].getConnection();
                PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                out.println(players[i].getCard());
            }
        }

    }

    void beginGame() {

        boolean runSecondWolf = true;
//        boolean runSecondMason = true; // TODO

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
//                            PlayerActions.seer(player, playOrder, this); //TODO
                            break;
                        case 7:
                            System.out.println("7 turn");
//                            PlayerActions.robber(player, playOrder, this); //TODO
                            break;
                        case 8:
                            System.out.println("8 turn");
//                            PlayerActions.troubleMaker(player, playOrder, this); //TODO
                            break;
                    }
                }
            }
        }

    }

    boolean isCenterCard(Player possibleCenter) {
        return (possibleCenter.getTurn() == 0 ||
                possibleCenter.getTurn() == 1 ||
                possibleCenter.getTurn() == 2);
    }

    void tellEveryone(String msg) {
        tellEveryone(msg, "null");
    }

    void tellEveryone(String msg, String mod) {
        String c;
        for (int i = 0; i < totalPlayers + 3; i++) {
            c = Integer.toString(players[i].getTurn());
            if ((!c.equals("0")) && !c.equals("1") && !c.equals("2")) {
                Socket skt = players[i].getConnection();
                PrintWriter out;
                try {
                    out = new PrintWriter(skt.getOutputStream(), true);
                    out.println(msg + ":" + mod);
                } catch (IOException e) {
                    System.out.println("Something went wrong in the tell everyone method!");
                    e.printStackTrace();
                }
            }
        }
    }

}