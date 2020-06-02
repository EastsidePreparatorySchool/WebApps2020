/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import java.util.HashMap;
import spark.Request;
import static spark.Spark.*;

//HANABI SERVER NB
public class Main {

    final static boolean DEBUG = true;

    //    static ArrayList<GameData> games = new ArrayList<>();
    final static String[] CARD_COLORS
            = new String[]{"Purple", "Green", "Yellow", "Blue", "Red"};

    final static int CARD_NUMBERS = 5;
    final static int CARD_DUPLICATES = 3;

//    static ArrayList<GameData> gameControls = new ArrayList<>();
    static GameControl gameControl;

    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<User> lobbyUsers = new ArrayList<>();

    static ArrayList<GameControl> gameControls;

    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("static");
        User.setup(args);

        gameControls = new ArrayList<>();

        //Making a test GameControl object
        
        
        Player p1 = new Player(new User("bar", "foo"), "Windows");
        Player p2 = new Player(new User("bar", "foo"), "MacOS");
        Player p3 = new Player(new User("bar", "foo"), "Linux");
        Player p4 = new Player(new User("blah", "blah1"), "RaspbianOS");
        
        p1.AddCardToHand(new Card("blue", 2));
        p1.AddCardToHand(new Card("red", 4));
        p1.AddCardToHand(new Card("yellow", 5));
        
        p2.AddCardToHand(new Card("orange", 5));
        p2.AddCardToHand(new Card("purple", 1));
        p2.AddCardToHand(new Card("blue", 1));
        
        p3.AddCardToHand(new Card("red", 3));
        p3.AddCardToHand(new Card("red", 2));
        p3.AddCardToHand(new Card("blue", 2));
        
        p4.AddCardToHand(new Card("orange", 5));
        p4.AddCardToHand(new Card("yellow", 4));
        p4.AddCardToHand(new Card("purple", 3));
        
        ArrayList<Player> testPlayers = new ArrayList<>();
        testPlayers.add(p1);
        testPlayers.add(p2);
        testPlayers.add(p3);
        testPlayers.add(p4);
        
        System.out.println(testPlayers.get(0).GetHand().getCards().get(0).color);
        
        GameData testGD = new GameData(testPlayers, 5, 30, "everyones favorite hanabi gamE", 0);
        GameControl testGC = new GameControl(testGD);
        gameControls.add(testGC);

        // get a silly route up for testing
        get("/hello", (req, res) -> {
            System.out.println("Hey we were invoked:");
            return "Hello world from code";
        });

        get("/load", (req, res) -> {
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
                // TODO: fix this user generation
                User user = new User("GenericUserName", "GenericUserID");
                ctx = new Context(user);
                System.out.println("context=" + ctx);
                System.out.println(user);
                map.put(tabid, ctx);
            }

            return ctx.toString();
        });

        get("/update", "application/json", (req, res) -> {
            String gameID = req.queryParams("gid");
            System.out.println("Update requested by " + req.ip() + " for game "
                    + gameID);

            if (gameID != null) { // Check for cor responding Game
                int gameID_int = Integer.parseInt(gameID);
                GameControl game = gameControls.get(gameID_int);
                GameData gameData = game.getGameData();
                //System.out.println("returning gamedata");
                return gameData;
            } else {
                System.out.println("returning all games");
                return gameControls;
            }
        }, new JSONRT());

        get("/turn", (req, res) -> {
            String turnJSON = req.queryParams("turn");
            String cardJSON = req.queryParams("card");
            //System.out.println(turnJSON + " | " + cardJSON);

            Turn turn = JSONRT.gson.fromJson(turnJSON, Turn.class);
            Card card = JSONRT.gson.fromJson(turnJSON, Card.class);

            //System.out.println("GGGG");

            Context ctx = getContext(req);
            if (ctx == null) {
                return "";
            }

            if (DEBUG) {
                GameData userGame = gameControls.get(turn.gameId).getGameData();
                userGame.debugNum++;
            }

            //TODO: implement non-debug game object modification
            return "";
        });

        put("/enter_game", (req, res) -> {
            // Get user ID and requested game ID
            String userID = req.queryParams("usr_id");
            String gameID = req.queryParams("game_id");

            for (User user
                    : lobbyUsers) { // Find this user in the lobby
                if (user.GetID().equals(userID)) {
                    for (GameControl game
                            : gameControls) { // Find this game
                        if (gameID.equals(game.getGameData().getid())) {
                            if (players.size() < 6) {
                                players.add(new Player(user)); // Create a new player with this user and add them to the game
                                lobbyUsers.remove(user); // Remove user from lobby
                                return "Entered user " + userID + " into game "
                                        + gameID;
                            } else {
                                return "Game " + gameID + " at max capacity";
                            }
                        }
                    }
                    return "Could not find game " + gameID;
                }
            }
            return "Could not find user " + userID;
        });
        put("/give_hint", (req, res) -> {
            String hintParam = req.queryParams("hint");

            Hint givenHint = JSONRT.gson.fromJson(hintParam, Hint.class);

            // Find this usesr and give them this hint
            for (GameControl game
                    : gameControls) {
                for (Player player
                        : game.getGameData().getPlayers()) {
                    final User playerUser = player.GetUser();

                    if (playerUser.GetID().equals(givenHint.playerToId)) {
                        // Validate Hint
                        boolean validHint = false;
                        for (Card card
                                : player.GetHand().getCards()) {
                            if (givenHint.isColor) {
                                if (card.color.equals(givenHint.hintContent)) {
                                    validHint = true;
                                    break;
                                }
                            } else {
                                if (card.number == Integer.parseInt(
                                        givenHint.hintContent)) {
                                    validHint = true;
                                    break;
                                }
                            }
                        }

                        if (validHint) {
                            player.ReceiveHint(givenHint);
                            return "Gave hint to user " + playerUser.GetID();
                        } else {
                            return "Hint to user " + playerUser.GetID()
                                    + " was invalid";
                        }
                    }
                }
            }

            return "Could not find user " + givenHint.playerToId;
        });
//        gameControl = new GameControl();
    }

    public static Context getContext(Request req) {
        spark.Session s = req.session();
        if (s.isNew()) {
            s.attribute("map", new HashMap<String, Context>());
        }
        HashMap<String, Context> map = s.attribute("map");
        //System.out.println("map =" + map);
        String tabid = req.headers("tabid");
        if (tabid == null) {
            tabid = "default";
            System.out.println(tabid);
        }
        Context ctx = map.get(tabid);

        return ctx;
    }
}
