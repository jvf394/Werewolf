package com.company;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by joeyf on 7/16/2016.
 */
public class PlayerActions {

    public static void wereWolf(Player player, Player[] playOrder, WerewolfProtocol wwp){

        wwp.tellEveryone("1");

        boolean isSecondWolf = false;
        for (Player p : playOrder) {
            if (p.getOrigCard() == 2) {
                isSecondWolf = true;
                wwp.tellEveryone("1",player.getName()+","+p.getName());
                try {
                    int lookingfor = Integer.parseInt(wwp.listenPlayer(player.getNum()));
                    for (Player returnPerson : playOrder) {
                        if (returnPerson.getTurn() == lookingfor) {
                            returnPerson.getCard();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (isSecondWolf == false){
            wwp.tellEveryone("1","none");
        }
    }

    public static void minion(Player player, Player[] playOrder, WerewolfProtocol wwp){

        wwp.tellEveryone("3");

        ArrayList werewolves = new ArrayList();
        for (Player p : playOrder){
            if(p.getOrigCard() == 1 || p.getOrigCard() == 2){
                werewolves.add(p.getName());
            }
        }
        wwp.tellEveryone("3",werewolves.toArray().toString());

    }

    public static void mason(Player player, Player[] playOrder, WerewolfProtocol wwp) {

        wwp.tellEveryone("4");

        boolean isSecondMason = false;
        for (Player p : playOrder) {
            if (p.getOrigCard() == 2) {
                isSecondMason = true;
                wwp.tellEveryone("4",player+","+p);
            }
        }
        if (isSecondMason == false){
            wwp.tellEveryone("4","none");
        }
    }


    public static void seer(Player player, Player[] playOrder, WerewolfProtocol werewolfProtocol) {



    }

    public static void robber(Player player, Player[] playOrder, WerewolfProtocol werewolfProtocol) {
    }

    public static void troubleMaker(Player player, Player[] playOrder, WerewolfProtocol werewolfProtocol) {
    }
}
