package fedor.games.werewolf;

import java.net.Socket;

@SuppressWarnings("unused")
class Player implements Comparable<Player> {

    private String sessionID;
    private int playerID;
    private String name;
    private int card;
    private int origCard;
    private Socket connection;
    private int turn;

    Player(String sessionID, int playerID, String name, int card, int origCard, Socket connection, int turn) {
        this.sessionID = sessionID;
        this.playerID = playerID;
        this.name = name;
        this.card = card;
        this.origCard = origCard;
        this.connection = connection;
        this.turn = turn;
    }

    String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                        "\tName: " + sessionID + "\n" +
                        "\tName: " + playerID + "\n" +
                        "\tNumber: " + name + "\n" +
                        "\tCard: " + card + "\n" +
                        "\tOriginal Card: " + origCard + "\n" +
                        "\tSocket: " + ((connection == null) ? "null" : connection.getRemoteSocketAddress().toString()) + "\n" +
                        "\tTurn: " + turn + "\n";
    }

}
