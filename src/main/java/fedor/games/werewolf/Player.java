package fedor.games.werewolf;

import java.net.Socket;

/**
 * Created by Joey on 11/27/2015.
 */
public class Player implements Comparable<Player> {

    private String name;
    private int num;
    private int card;
    private int origCard;
    private Socket connection;
    private int turn;

    public Player(String name, int num, int card, int origCard, Socket connection, int turn) {
        this.name = name;
        this.num = num;
        this.card = card;
        this.origCard = origCard;
        this.connection = connection;
        this.turn = turn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public int getOrigCard() {
        return origCard;
    }

    public void setOrigCard(int origCard) {
        this.origCard = origCard;
    }

    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(card, o.getCard());
    }

    @Override
    public String toString() {
        return
                "PLAYER INFORMATION\n" +
                        "\tName: " + name + "\n" +
                        "\tNumber: " + num + "\n" +
                        "\tCard: " + card + "\n" +
                        "\tOriginal Card: " + origCard + "\n" +
                        "\tSocket: " + ((connection == null) ? "null" : connection.getRemoteSocketAddress().toString()) + "\n" +
                        "\tTurn: " + turn + "\n";
    }

}