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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

class GuiCreator {

    private static Player me = new Player("","",-1,null);
    private static Stage wwStage = new Stage();
    private static String deckStr;

    public static String gameGui() {
        //Create Elements
        GridPane grid = new GridPane();
        Text usernameTxt = new Text("Username:");
        TextField usernameField = new TextField("Username");
        Text usernameError = new Text("*please enter a valid username");
        usernameError.getStyleClass().add("errorText");
        Text gameSessionText = new Text("Session Name:");
        TextField sessionNameField = new TextField("Game Name");
        Text sessionError = new Text("*please enter a valid session name");
        sessionError.getStyleClass().add("errorText");
        Button hostBtn = new Button("Host");
        Button joinBtn = new Button("Join");
        Scene gameScene = new Scene(grid);
        gameScene.getStylesheets().add("style.css");

        //Add Elements
        grid.add(usernameTxt,0,0);
        grid.add(usernameField,1,0);
        grid.add(gameSessionText,0,1);
        grid.add(sessionNameField,1,1);
        grid.add(hostBtn, 0, 2);
        grid.add(joinBtn, 0, 3);

        //Handle Buttons
        hostBtn.setOnAction(event -> {
            if (!usernameField.getText().equals("") && !usernameField.getText().equals("Username")) {
                if (!sessionNameField.getText().equals("Game Name")) {
                    me.setUsername(usernameField.getText());
                    me.setSessionName("host;" + sessionNameField.getText());
                    wwStage.close();
                }else if (!grid.getChildren().contains(sessionError)) grid.add(sessionError,2,1);
            } else if (!grid.getChildren().contains(usernameError))grid.add(usernameError,2,0);
        });
        joinBtn.setOnAction(event -> {
            if (!usernameField.getText().equals("") && !usernameField.getText().equals("Username")) {
                if (!sessionNameField.getText().equals("Game Name")) {
                    me.setUsername(usernameField.getText());
                    me.setSessionName("join;" + sessionNameField.getText());
                    wwStage.close();
                }else if (!grid.getChildren().contains(sessionError)) grid.add(sessionError,2,1);
            } else if (!grid.getChildren().contains(sessionError)) grid.add(usernameError,2,0);
        });

        //Launch Stage
        wwStage.setAlwaysOnTop(true);
        wwStage.setScene(gameScene);
        wwStage.setFullScreen(true);
        wwStage.showAndWait();
        return me.getSessionName() + ";" + me.getUsername();
    }

    public static String characterSelectionGui() {
        //Initialize Variables
        String[] characterNames = {"Doppelganger", "Werewolf", "Werewolf", "Minion", "Mason", "Mason", "Seer", "Robber", "Troublemaker", "Drunk", "Insomniac", "Hunter", "Tanner", "Villager", "Villager", "Villager"};
        ArrayList<Button> Btns = new ArrayList<>();
        ArrayList<Text> status = new ArrayList<>();
        final int[] numPlayers = {0};

        //Create Elements
        GridPane grid = new GridPane();
        Text question = new Text("Which of these will be playing?");
        Text players = new Text("Number of Players: " + numPlayers[0]);
        Button continueBtn = new Button("Continue");
        Scene characterSelectionScene = new Scene(grid);
        characterSelectionScene.getStylesheets().add("style.css");

        //Add Elements
        grid.add(question, 0, 0, 3, 1);
        grid.add(players, 3, 0, 2, 1);
        grid.add(continueBtn, 0, 4);

        //Handle Buttons
        continueBtn.setOnAction(event1 -> {
            if (numPlayers[0]>=2) {
                if (status.get(4).getText().equals(status.get(5).getText())) {
                    deckStr = "";
                    for (int i = 0; i < 16; i++) {
                        if (status.get(i).getText().equals("yes")) {
                            deckStr += characterNames[i] + ";";
                        }
                    }
                    deckStr = numPlayers[0]+";"+deckStr;
                    deckStr = deckStr.substring(0, deckStr.length() - 1);
                    String[] deck = deckStr.split(",");
                    //Collections.shuffle(Arrays.asList(deck));
                    deckStr = Arrays.toString(deck);
                    deckStr = deckStr.substring(1, deckStr.length() - 1);
                    deckStr = deckStr.replace(" ", "");
                    wwStage.close();
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
                    if (status.get(Btns.indexOf(cardBtn)).getText().equalsIgnoreCase("no")) {
                        numPlayers[0]++;
                        vContainer.setStyle("-fx-background-color: rgba(0,100,0,1); -fx-background-radius: 10px; -fx-border-color: rgba(255,255,255,.5); -fx-border-radius: 10px");
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
                    vContainer.setStyle("-fx-background-color: rgba(0,100,0,1); -fx-background-radius: 10px; -fx-border-color: rgba(255,255,255,.5); -fx-border-radius: 10px");
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

        //Launch Stage
        wwStage.setTitle(me.getSessionName().split(";")[1]);
        wwStage.setFullScreen(false);
        wwStage.setScene(characterSelectionScene);
        wwStage.showAndWait();
        return deckStr;
    }

    public static String showCardGui(int card,int iteration) {
        //Initialize Variable
        me.setCard(card);

        // Create Elements
        GridPane grid = new GridPane();
        Text titleTxt = new Text();
        if (iteration==1) titleTxt.setText(me.getUsername() + ", you start as:");
        if (iteration==2) titleTxt.setText(me.getUsername() + ", this is what you started and ended with:");
        if (iteration==3) titleTxt.setText(me.getUsername() + ", you are currently:");
        Image cardImg = new Image(me.getCard() + ".png", 100, 548, true, true);
        ImageView cardView = new ImageView(cardImg);
        Scene showCardScene = new Scene(grid);
        showCardScene.getStylesheets().add("style.css");

        //Add Elements
        grid.add(titleTxt, 0, 0);
        grid.add(cardView, 0, 1);

        //Handle Delay
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> wwStage.close());
        delay.play();

        //Launch Stage
        wwStage.setScene(showCardScene);
        wwStage.showAndWait();
        return "done";
    }

    public String takeTurnGui(String info) {
        //Initialize Variables
        int card = Integer.parseInt((info.split(":")[0]));
        String modifier = info.split(":")[1];

        // Create Elements
        GridPane grid = new GridPane();
        Text feedBack = new Text("");
        Text mainTxt = new Text(me.getUsername() + ", ");
        Text text = new Text();
        Image cardImg = new Image(card + ".png", 50, 274, true, true);
        ImageView cardView = new ImageView(cardImg);
        Scene showCardScene = new Scene(grid);
        showCardScene.getStylesheets().add("style.css");

        //Handle Delay
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
                        wwStage.close();
                    });
                    middle.setOnAction(event -> {
                        feedBack.setText("1");
                        wwStage.close();
                    });
                    right.setOnAction(event -> {
                        feedBack.setText("2");
                        wwStage.close();
                    });
                } else {
                    if (me.getUsername().equals(modifier.split(",")[0])) {
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

        //Launch Stage
        wwStage.setScene(showCardScene);
        wwStage.showAndWait();
        return feedBack.getText();
    }
}