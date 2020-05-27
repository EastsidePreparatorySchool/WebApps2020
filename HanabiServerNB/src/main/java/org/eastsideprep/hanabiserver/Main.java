/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.CardSpotInterface;
import static spark.Spark.*;

//HANABI SERVER NB
public class Main {

    final static boolean DEBUG = true;
    final static Gson gson = new Gson();

    final static String[] CARD_COLORS = new String[]{"Purple", "Green", "Yellow", "Blue", "Red"};
    final static int CARD_NUMBERS = 5;
    final static int CARD_DUPLICATES = 3;

    static ArrayList<Game> games = new ArrayList<>();
    static int gameIdStep = 0;
    static GameControl gameControl = new GameControl();

    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("static");
        User.setup(args);

        /*
        get("/load", (Request req, Response res) -> {
            // Open new, independent tab
            spark.Session s = req.session();

         
            // if the session is new, make sure it has a context map
            if (s.isNew()) {
                s.attribute("map", new HashMap<String, Context>());
            }

            // now we can safely access the context map whether the session is new or not
            HashMap<String, Context> map = s.attribute("map");

            // find the context that goes with the tab
            String tabid = req.headers("tabid");
            if (tabid == null) {
                tabid = "default";
                System.out.println(tabid);
            }
            Context ctx = map.get(tabid);
            System.out.println("tabid =" + tabid);

            // no context? no problem.
            if (ctx == null) {
               User user = new User(); 
                ctx = new Context(user);
                System.out.println("context=" + ctx);
                System.out.println(user);
                map.put(tabid, ctx);
            }
            System.out.println(tabid);
            return ctx.toString();
        });
        
         */
        get("/update", (req, res) -> {
            Context ctx = getContext(req);
            if (ctx == null) {return "";}

            if (ctx.user.getGameId() == null) {
                if (DEBUG) {
                    createGame(new ArrayList<>(Arrays.asList(ctx.user)));
                } else {
                    throw new Exception();
                }
            }
            Game userGame = games.get(ctx.user.getGameId());
            //userGame.debug++;

            String gameJSON = gson.toJson(userGame);
            //System.out.println(gameJSON);

            return gameJSON;
        }
        );

        get("/turn", (req, res) -> {
            String turnJSON = req.queryParams("json");
            //System.out.println(turnJSON);
            Turn turn = gson.fromJson(turnJSON, Turn.class);
            
            Context ctx = getContext(req);
            if (ctx == null) {return "";}
            Game userGame = games.get(ctx.user.getGameId());
            
            if (turn.debug > 0) {
                userGame.debug += turn.debug;
            }
            
            return "";
        });

    }

    public static Context getContext(Request req) {
        spark.Session s = req.session();
        if (s.isNew()) {
            s.attribute("map", new HashMap<String, Context>());
        }
        HashMap<String, Context> map = s.attribute("map");
        System.out.println("map =" + map);
        String tabid = req.headers("tabid");
        if (tabid == null) {
            tabid = "default";
            System.out.println(tabid);
        }
        Context ctx = map.get(tabid);

        return ctx;
    }

    public static void createGame(ArrayList<User> users) {

        ArrayList<Card> tempDeck = new ArrayList<>();
        for (int cardNumber = 1; cardNumber <= CARD_NUMBERS; cardNumber++) {
            for (String cardColor : CARD_COLORS) {
                for (int i = 0; i < CARD_DUPLICATES; i++) {
                    tempDeck.add(new Card(cardColor, cardNumber));
                }
            }
        }
        Deck deck = new Deck(tempDeck);
        gameControl.shuffle(deck);

        HashMap<String, PlayedCards> playedCards = new HashMap<>();
        for (String color : CARD_COLORS) {
            playedCards.put(color, new PlayedCards(color));
        }

        Discard discards = new Discard();

        ArrayList<Player> players = new ArrayList<>(); // TODO: populate this with Users in a room
        users.forEach((user) -> {
            players.add(new Player(user, new Hand(user.getName() + "\' hand")));
        });

        Game game = new Game(gameIdStep, players, deck, playedCards, discards);
        games.add(gameIdStep, game);
        gameIdStep++;

        users.forEach((user) -> {
            user.setGameId(game.getId());
        });

        players.forEach((player) -> {
            for (int i = 0; i < game.getMaxCardsInHand(); i++) {
                player.AddCardToHand(deck.draw());
            }
        });

    }
}
