package fedor.games.werewolf;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiLauncher extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Initializes the GuiCreator
        GuiCreator wwgc = new GuiCreator();
        wwgc.gameGui();
        //wwgc.usernameGui(1);
        //wwgc.usernameGui(2);
        //System.out.println(wwgc.characterSelectionGui());
        //wwgc.showCardGui(1,"Username");
        //wwgc.takeTurnGui("1:test", new Player("username",2,3,4, new Socket(),5));
    }
}