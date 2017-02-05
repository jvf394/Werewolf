package fedor.games.werewolf;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class GuiCreator {

    private static String playerName, name = "", host, sessionName;
    private static int numberOfPlayers = 0;


    public static String[] gameGui() {
        Stage gameStage = new Stage();
        gameStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        Button hostBtn = new Button("Host");
        TextField sessionNameField = new TextField("Game Name");
        Button joinBtn = new Button("Join");
        grid.add(hostBtn, 0, 0);
        grid.add(sessionNameField, 1, 0);
        grid.add(joinBtn, 0, 1);
        hostBtn.setOnAction(event -> {
            if (!sessionNameField.getText().equals("Game Name")) {
                host = "0";
                sessionName = sessionNameField.getText();
            }
        });
        joinBtn.setOnAction(event -> {
            if (!sessionNameField.getText().equals("Game Name")) {
                host = "0";
                sessionName = sessionNameField.getText();
            }
        });
        Scene gameScene = new Scene(grid);
        gameScene.getStylesheets().add("style.css");
        gameStage.setScene(gameScene);
        gameStage.setFullScreen(true);
        gameStage.showAndWait();
        return new String[]{host, sessionName};
    }

    public static String[] usernameGui(int turn) {
        Stage usernameStage = new Stage();
        usernameStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        Text usernameTxt = new Text("Username:");
        TextField usernameField = new TextField("Username");
        Text numberOfPlayersTxt = new Text("Number of Players:");
        TextField numberOfPlayersField = new TextField("2");
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            playerName = usernameField.getText();
            numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());
            if (turn == 1) {
                if ((playerName != null) && (!playerName.equals(""))) {
                    if ((numberOfPlayers > 1) && (numberOfPlayers < 11)) {
                        name = playerName;
                    }
                }
            } else if ((playerName != null) && (!playerName.equals(""))) {
                name = playerName;
            }
            usernameStage.close();
        });
        grid.add(usernameTxt, 0, 0);
        grid.add(usernameField, 1, 0);
        usernameStage.setTitle("Client");
        if (turn == 1) {
            usernameStage.setTitle("Host");
            grid.add(numberOfPlayersTxt, 0, 1);
            grid.add(numberOfPlayersField, 1, 1);
        }
        grid.add(submitBtn, 0, 2);
        Scene usernameScene = new Scene(grid);
        usernameScene.getStylesheets().add("style.css");
        usernameStage.setScene(usernameScene);
        usernameStage.setFullScreen(true);
        usernameStage.showAndWait();
        return new String[]{name, String.valueOf(numberOfPlayers)};
    }

    private static String deckStr;

    public static String characterSelectionGui() {
        Stage characterSelectionStage = new Stage();
        characterSelectionStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        String[] characterNames = {"Doppelganger", "Werewolf", "Werewolf", "Minion", "Mason", "Mason", "Seer", "Robber", "Troublemaker", "Drunk", "Insomniac", "Hunter", "Tanner", "Villager", "Villager", "Villager"};
        ArrayList<Button> Btns = new ArrayList<>();
        ArrayList<Text> status = new ArrayList<>();

        final int[] numPlayers = {0};
        Text question = new Text("Which of these will be playing?");
        Text players = new Text("Number of Players: " + numPlayers[0]);
        Button continueBtn = new Button("Continue");
        grid.add(question, 0, 0, 3, 1);
        grid.add(players, 3, 0, 2, 1);
        grid.add(continueBtn, 0, 4);
        continueBtn.setOnAction(event1 -> {
            if (numberOfPlayers == numPlayers[0]) {
                if (status.get(4).getText().equals(status.get(5).getText())) {
                    deckStr = "";
                    for (int i = 0; i < 16; i++) {
                        if (status.get(i).getText().equals("yes")) {
                            deckStr += i + ",";
                        }
                    }
                    deckStr = deckStr.substring(0, deckStr.length() - 1);
                    String[] deck = deckStr.split(",");
                    //Collections.shuffle(Arrays.asList(deck));
                    deckStr = Arrays.toString(deck);
                    deckStr = deckStr.substring(1, deckStr.length() - 1);
                    deckStr = deckStr.replace(" ", "");
                    characterSelectionStage.close();
                }
            }
        });
        int count = 0;
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 8; c++) {
                Text statusTxt = new Text("no");
                VBox vContainer = new VBox();
                HBox hContainer = new HBox();
                status.add(statusTxt);
                Image cardImg = new Image(count + ".png", 100, 548, true, true);
                ImageView imageView = new ImageView(cardImg);
                Button cardBtn = new Button("",imageView);
                cardBtn.setStyle("-fx-padding: 0%; -fx-background-color: rgba(0,0,0,0);");
                cardBtn.setOnAction(event -> {
                    System.out.println(characterNames[Btns.indexOf(cardBtn)]);
                    System.out.println(status.get(Btns.indexOf(cardBtn)).getText());
                    if (status.get(Btns.indexOf(cardBtn)).getText().equalsIgnoreCase("no")) {
                        numPlayers[0]++;
                        vContainer.setStyle("-fx-background-color: rgba(0,30,0,1); -fx-background-radius: 10px; -fx-border-color: rgba(255,255,255,.5); -fx-border-radius: 10px");
                        status.get(Btns.indexOf(cardBtn)).setText("yes");
                    }else{
                        numPlayers[0]--;
                        vContainer.setStyle("-fx-background-color: rgba(0,0,0,0.2); -fx-background-radius: 10px; -fx-border-color: rgba(0,0,0,.5); -fx-border-radius: 10px");
                        status.get(Btns.indexOf(cardBtn)).setText("no");
                    }
                    if(numPlayers[0]>=0) players.setText("Number of Players: " + numPlayers[0]);
                });
                Btns.add(cardBtn);
                vContainer.setPadding(new Insets(5, 5, 5, 5));
                vContainer.minWidth(200);
                vContainer.setStyle("-fx-background-color: rgba(0,0,0,0.2); -fx-background-radius: 10px; -fx-border-color: rgba(0,0,0,.5); -fx-border-radius: 10px");
                if (count == 1 || count == 2 || count == 3)
                    vContainer.setStyle("-fx-background-color: rgba(0,30,0,1); -fx-background-radius: 10px; -fx-border-color: rgba(255,255,255,.5); -fx-border-radius: 10px");
                hContainer.setSpacing(10);
                hContainer.setPadding(new Insets(5, 10, 5, 10));
                hContainer.getChildren().add(cardBtn);
                hContainer.setAlignment(Pos.CENTER);
                vContainer.setAlignment(Pos.CENTER);
                vContainer.getChildren().add(imageView);
                vContainer.getChildren().add(hContainer);
                grid.add(vContainer, c, r + 1);
                count++;
            }
        }
        status.get(1).setText("yes");
        status.get(2).setText("yes");
        status.get(3).setText("yes");
        Scene characterSelectionScene = new Scene(grid);
        characterSelectionScene.getStylesheets().add("style.css");
        characterSelectionStage.setScene(characterSelectionScene);
        characterSelectionStage.showAndWait();
        return deckStr;
    }

    void showCardGui(int card, String name) {
        Stage showCardStage = new Stage();
        showCardStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        Scene showCardScene = new Scene(grid);
        showCardScene.getStylesheets().add("style.css");
        showCardStage.setScene(showCardScene);
        Text titleTxt = new Text(name + ", you start as:");
        grid.add(titleTxt, 0, 0);
        Image cardImg = new Image(card + ".png", 100, 548, true, true);
        ImageView cardView = new ImageView(cardImg);
        grid.add(cardView, 0, 1);
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> showCardStage.close());
        delay.play();
        showCardStage.showAndWait();
    }

    public String takeTurnGui(String info, Player me) {
        System.out.println(info);
        int card = Integer.parseInt((info.split(":")[0]));
        String modifier = info.split(":")[1];
        System.out.println(modifier);
        Text feedBack = new Text("");
        Stage takeTurnStage = new Stage();
        takeTurnStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        Image cardImg = new Image(card + ".png", 50, 274, true, true);
        ImageView cardView = new ImageView(cardImg);
        Text mainTxt = new Text(me.getName() + ", ");
        Text text = new Text();
        Scene showCardScene = new Scene(grid);
        showCardScene.getStylesheets().add("style.css");
        takeTurnStage.setScene(showCardScene);
        switch (card) {
            case 1: //Werewolf
                feedBack.setText("0");
                if (modifier.equals("none")) {
                    mainTxt.setText(mainTxt.getText() + "you are alone, choose a middle card to view");
                    grid.add(mainTxt, 0, 0, 3, 1);
                    Button left = new Button("Left");
                    Button middle = new Button("Middle");
                    Button right = new Button("Right");
                    grid.add(left, 0, 1);
                    grid.add(middle, 1, 1);
                    grid.add(right, 2, 1);
                    left.setOnAction(event -> {
                        feedBack.setText("0");
                        takeTurnStage.close();
                    });
                    middle.setOnAction(event -> {
                        feedBack.setText("1");
                        takeTurnStage.close();
                    });
                    right.setOnAction(event -> {
                        feedBack.setText("2");
                        takeTurnStage.close();
                    });
                } else {
                    if (me.getName().equals(modifier.split(",")[0])) {
                        text.setText(modifier.split(",")[1]);
                        mainTxt.setText(mainTxt.getText() + "the other werewolf is " + text.getText());
                        grid.add(mainTxt, 0, 0);
                    } else {
                        text.setText(modifier.split(",")[0]);
                        mainTxt.setText(mainTxt.getText() + "the other werewolf is " + text.getText());
                        grid.add(mainTxt, 0, 0);
                    }
                }
                break;
            case 3: //Minion
                text.setText(modifier);
                mainTxt.setText("the werewolves are: " + text.getText());
                grid.add(mainTxt, 0, 0);
                break;
            //seer
            case 6:
                grid.add(cardView, 0, 1);
                break;
            //robber
            case 7:
                grid.add(cardView, 0, 1);
                break;
            //troublemaker
            case 8:
                grid.add(cardView, 0, 1);
                break;
            //drunk
            case 9:
                grid.add(cardView, 0, 1);
                break;
            //insomniac
            case 10:
                grid.add(cardView, 0, 1);
                break;
        }
        takeTurnStage.showAndWait();
        System.out.println(feedBack.getText());
        return feedBack.getText();
    }

//    public void updateCardGui() { //TODO
//
//    }
}