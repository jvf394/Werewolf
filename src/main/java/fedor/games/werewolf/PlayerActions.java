package fedor.games.werewolf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

class PlayerActions {

    static void wereWolf(Player player, Player[] playOrder, WerewolfProtocol wwp){

        wwp.tellEveryone("1");

        boolean isSecondWolf = false;
        for (Player p : playOrder) {
            if (p.getOrigCard() == 2 && !wwp.isCenterCard(p) && !p.equals(player)) {
                System.out.println("you are not a lone wolf");
                isSecondWolf = true;
                wwp.tellEveryone("1",player.getName()+","+p.getName());
            }
        }
        if (!isSecondWolf){
            wwp.tellEveryone("1","none");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(player.getConnection().getInputStream()));
                int lookingfor = Integer.parseInt(in.readLine());
                for (Player returnPerson : playOrder) {
                    if (returnPerson.getTurn() == lookingfor) {
                        wwp.tellEveryone(""+returnPerson.getCard());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unused")
	static void minion(Player player, Player[] playOrder, WerewolfProtocol wwp){

        wwp.tellEveryone("3");

        ArrayList<String> werewolves = new ArrayList<>();
        for (Player p : playOrder){
            if(p.getOrigCard() == 1 || p.getOrigCard() == 2){
                werewolves.add(p.getName());
            }
        }
        wwp.tellEveryone("3", Arrays.toString(werewolves.toArray()));

    }

    static void mason(Player player, Player[] playOrder, WerewolfProtocol wwp) {

        wwp.tellEveryone("4");

        boolean isSecondMason = false;
        for (Player p : playOrder) {
            if (p.getOrigCard() == 2) {
                isSecondMason = true;
                wwp.tellEveryone("4",player+","+p);
            }
        }
        if (!isSecondMason){
            wwp.tellEveryone("4","none");
        }
    }

//TODO
//    static void seer(Player player, Player[] playOrder, WerewolfProtocol werewolfProtocol) {
//    }

//    static void robber(Player player, Player[] playOrder, WerewolfProtocol werewolfProtocol) {
//    }

//    static void troubleMaker(Player player, Player[] playOrder, WerewolfProtocol werewolfProtocol) {
//    }
}
