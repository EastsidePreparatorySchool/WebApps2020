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

    final static boolean DEBUG = true;
    
 //    static ArrayList<GameData> games = new ArrayList<>();
    final static String[] CARD_COLORS = new String[]{"Purple", "Green", "Yellow", "Blue", "Red"};

    final static int CARD_NUMBERS = 5;
    final static int CARD_DUPLICATES = 3;

//    static ArrayList<GameData> gameControls = new ArrayList<>();
    static GameControl gameControl;

    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<User> lobbyUsers = new ArrayList<>();

    static ArrayList<GameControl> gameControls;
    static ArrayList<Game> games;

    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("static");
        User.setup(args);

        gameControls = new ArrayList<>();

        //Making a test GameControl object
        ArrayList<Player> testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Windows"));
        testPlayers.add(new Player("MacOS"));
        testPlayers.add(new Player("Linux"));
        GameData testGD = new GameData(testPlayers, 5, 30, "a game", 0);
        GameControl testGC = new GameControl(testGD);
        gameControls.add(testGC);

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
            System.out.println("Update requested by " + req.ip() + " for game " + gameID);

            if (gameID != null) {
                int gameID_int = Integer.parseInt(gameID);
                GameControl game = gameControls.get(gameID_int);
                GameData gameData = game.getGameData();
                System.out.println("returning gamedata");
                return gameData;
            } else {
                System.out.println("returning games");
                return gameControls;
            }
        }, new JSONRT());

        get("/turn", (req, res) -> {
            String turnJSON = req.queryParams("turn");
            String cardJSON = req.queryParams("card");
            System.out.println(turnJSON + " | " + cardJSON);

            Turn turn = JSONRT.gson.fromJson(turnJSON, Turn.class);
            Card card = JSONRT.gson.fromJson(turnJSON, Card.class);
            
            System.out.println("GGGG");
            
            Context ctx = getContext(req);
            if (ctx == null) {return "";}
            
            if (DEBUG) {
             GameData userGame = games.get(turn.gameId).getGameData();
             userGame.debugNum++;
            }
            
            //TODO: implement non-debug game object modification
               
            return "";
        });
        
        // TODO: handle expected params
        put("/enter_game", (req, res) -> {
            // Get user ID and requested game ID
            String userID = req.queryParams("usr_id");
            String gameID = req.queryParams("game_id");

            for (User user
                    : lobbyUsers) { // Find this user in the lobby
                if (user.GetID().equals(userID)) {
                    for (Game game
                            : games) { // Find this game
                        if (game.GetGameID().equals(gameID)) {
                            players.add(new Player(user)); // Create a new player with this user and add them to the game
                            lobbyUsers.remove(user); // Remove user from lobby
                            break;
                        }
                    }
                    break;
                }
            }
            
            return "Entered user " + userID + " into game " + gameID;
        });
//        gameControl = new GameControl();
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
}
