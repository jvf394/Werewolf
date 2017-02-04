package fedor.games.werewolf;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Fedor on 11/28/2015.
 */
public class CharacterSelectionGui  extends Application {
    Socket wwSocket;
    PrintWriter out;
    String deckStr = null;
    public void setSocket(Socket wwSocket){
        try {
            out = new PrintWriter(wwSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
    launch(args);
    }
    @Override
    public void start(Stage characterOptions) throws Exception {

}}
