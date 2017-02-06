package fedor.games.werewolf;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


class WerewolfServer{

    private final static int PORT_NUMBER = 1234;
    private static int totalPlayers;
    private static ServerSocket serverSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static WerewolfProtocol wwp;


	public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            wwp = new WerewolfProtocol();

            Socket clientSocket = serverSocket.accept();
            System.out.println("player accepted");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("1");
            out.println("2");

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORT_NUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}