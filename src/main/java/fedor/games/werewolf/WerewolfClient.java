package fedor.games.werewolf;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WerewolfClient extends Application {
    private static PrintWriter out;
    private static BufferedReader in;
    private int gameStatus = 0;
    //Initializes the GuiCreator
    GuiCreator wwgc = new GuiCreator();


    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
//        String hostName = "45.55.65.225";
        int portNumber = 1234;

        Socket wwSocket = new Socket(hostName, portNumber);
        out = new PrintWriter(wwSocket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(wwSocket.getInputStream()));
        BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Do what server says
        do {
            doAction(messageIn());
        } while (gameStatus == 1);
        System.exit(1);
    }

    public void doAction(String[] str) {
        switch (str[0]) {
            case "Game Request:":
                gameStatus = 1;
                messageOut(str[0], wwgc.gameGui());
                break;
            case "Character Selection:":
                messageOut(str[0], wwgc.characterSelectionGui());
                break;
            case "View your Card:":
                messageOut(str[0], wwgc.showCardGui(Integer.parseInt(str[1].split(";")[0]),Integer.parseInt(str[1].split(";")[1])));
                break;
            case "Take turn:":
                messageOut(str[0], wwgc.takeTurnGui("he"));
                break;
            case "End Game:":
                messageOut(str[0], wwgc.showCardGui(Integer.parseInt(str[1].split(";")[0]),Integer.parseInt(str[1].split(";")[1])));
                break;
            default:
                System.out.println("something went wrong");
                break;
        }

    }


    public String[] messageIn() throws IOException {
        String header = new String(in.readLine());
        System.out.println("here");
        String body = new String(in.readLine());
        return new String[]{header, body};
    }

    public void messageOut(String header, String body) {
        String[] message = new String[]{header, body};
        out.println(header);
        out.println(body);
    }
}
