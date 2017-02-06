package fedor.games.werewolf;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class GuiLauncher extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Initializes the GuiCreator
        GuiCreator wwgc = new GuiCreator();
        System.out.println("Game Request:"+wwgc.gameGui().toString());
        System.out.println("Character Selection:"+wwgc.characterSelectionGui());
        System.out.println("View your Card:"+wwgc.showCardGui(1,1));
        System.out.println("Take turn:"+wwgc.takeTurnGui("1:none"));
        System.out.println("End Game:"+wwgc.showCardGui(9,3));
        System.out.println("End Game:"+wwgc.showCardGui(1,2));
    }
}