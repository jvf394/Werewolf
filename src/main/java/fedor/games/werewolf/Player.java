package fedor.games.werewolf;

import java.net.Socket;

@SuppressWarnings("unused")
class Player implements Comparable<Player> {

    private String name;
    private int num;
    private int card;
    private int origCard;
    private Socket connection;
    private int turn;

    Player(String name, int num, int card, int origCard, Socket connection, int turn) {
        this.name = name;
        this.num = num;
        this.card = card;
        this.origCard = origCard;
        this.connection = connection;
        this.turn = turn;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    int getCard() {
        return card;
    }

    void setCard(int card) {
        this.card = card;
    }

    int getOrigCard() {
        return origCard;
    }

    void setOrigCard(int origCard) {
        this.origCard = origCard;
    }

    Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    int getTurn() {
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
