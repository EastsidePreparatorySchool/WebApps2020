/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.CardSpotInterface;
import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.*;

//HANABI SERVER NB
public class Main {

    final static String[] CARD_COLORS = new String[]{"Purple", "Green", "Yellow", "Blue", "Red"};

    final static int CARD_NUMBERS = 5;
    final static int CARD_DUPLICATES = 3;

//    static ArrayList<GameData> games = new ArrayList<>();
    static GameControl gameControl;

    static ArrayList<Player> players = new ArrayList<>();

    static ArrayList<GameControl> games;

    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("static");
        User.setup(args);

        games = new ArrayList<>();

        //Making a test GameControl object
        ArrayList<Player> testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Windows"));
        testPlayers.add(new Player("MacOS"));
        testPlayers.add(new Player("Linux"));
        GameData testGD = new GameData(testPlayers, 5, 30, "a game", 0);
        GameControl testGC = new GameControl(testGD);
        games.add(testGC);

        // get a silly route up for testing
        get("/hello", (req, res) -> {
            System.out.println("Hey we were invoked:");
            return "Hello world from code";
        });

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

            return ctx.toString();
        });

        get("/update", "application/json", (req, res) -> {
            String gameID = req.queryParams("gid");
            System.out.println("Update requested by " + req.ip() + " for game " + gameID);

            if (gameID != null) {
                int gameID_int = Integer.parseInt(gameID);
                GameControl game = games.get(gameID_int);
                GameData gameData = game.getGameData();
                return gameData;
            } else {
                System.out.println("returning games");
                return games;
            }
        }, new JSONRT());

        put("/turn", (req, res) -> {
            return "/turn route";
        });
//        gameControl = new GameControl();
    }

    public static void createGame() {

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

        GameData game = new GameData(players, deck, playedCards, discards);
        GameControl gc = new GameControl(game);
        games.add(gc); // "players" here needs to become a subset

        players.forEach((player) -> {
            for (int i = 0; i < game.getMaxCardsInHand(); i++) {
                player.AddCardToHand(deck.draw());
            }
        });

    }
}
