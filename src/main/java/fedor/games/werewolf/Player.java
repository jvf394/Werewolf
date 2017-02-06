package fedor.games.werewolf;

import java.net.Socket;

@SuppressWarnings("unused")
class Player implements Comparable<Player> {

    private String sessionName;
    private String username;
    private int card;
    private Socket connection;

    Player(String sessionName, String username, int card, Socket connection) {
        this.sessionName = sessionName;
        this.username = username;
        this.card = card;
        this.connection = connection;
    }

    String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    int getCard() {
        return card;
    }

    void setCard(int card) {
        this.card = card;
    }

    Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(card, o.getCard());
    }

    @Override
    public String toString() {
        return
                "PLAYER INFORMATION\n" +
                        "\tSessionName: " + sessionName + "\n" +
                        "\tUsername: " + username + "\n" +
                        "\tCard: " + card + "\n" +
                        "\tSocket: " + ((connection == null) ? "null" : connection.getRemoteSocketAddress().toString()) + "\n";
    }

}
