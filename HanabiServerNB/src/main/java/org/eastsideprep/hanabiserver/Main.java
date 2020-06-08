/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import java.util.HashMap;
import spark.Request;
import spark.Response;
import static spark.Spark.*;
import org.eastsideprep.hanabiserver.interfaces.GameControlInterface;

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
        Player p1 = new Player(new User("randomperson@yahoo.com", "foo1", true), 1);
        Player p2 = new Player(new User("gunnarmein@eastsideprep.org", "foo2", true), 2);
        Player p3 = new Player(new User("keyboardtyping@lol.net", "foo3", true), 3);
        Player p4 = new Player(new User("blah4x@gmail.com", "blah4", true), 4);
        Player p5 = new Player(new User("Windows@google.com", "foo", true), 5);
//        Player p6 = new Player(new User("macOS@google.com", "fooy", true), 6);
        
        p1.AddCardToHand(new Card("blue", 2));
        p1.AddCardToHand(new Card("red", 4));
        p1.AddCardToHand(new Card("yellow", 5));

        p2.AddCardToHand(new Card("orange", 5));
        p2.AddCardToHand(new Card("purple", 1));
        p2.AddCardToHand(new Card("blue", 1));

        p3.AddCardToHand(new Card("red", 3));
        p3.AddCardToHand(new Card("red", 2));
        p3.AddCardToHand(new Card("blue", 2));

        p4.AddCardToHand(new Card("orange", 4));
        p4.AddCardToHand(new Card("yellow", 4));
        p4.AddCardToHand(new Card("purple", 3));

        p5.AddCardToHand(new Card("red", 5));
        p5.AddCardToHand(new Card("yellow", 3));
        p5.AddCardToHand(new Card("blue", 3));

//        p6.AddCardToHand(new Card("blue", 5));
//        p6.AddCardToHand(new Card("red", 4));
//        p6.AddCardToHand(new Card("purple", 1));

        ArrayList<Player> testPlayers = new ArrayList<>();
        testPlayers.add(p1);
        testPlayers.add(p2);
        testPlayers.add(p3);
        testPlayers.add(p4);
        testPlayers.add(p5);
//        testPlayers.add(p6);

//        System.out.println(testPlayers.get(0).GetHand().getCards().get(0).color);
        GameData testGD = new GameData(testPlayers, 3, 30, "game for aybala", 0);
        GameControl testGC = new GameControl(testGD);
        gameControls.add(testGC);

        Player g2p1 = new Player(new User("eoreizy@eastsideprep.org", "blah1", true),
                1);
        Player g2p2 = new Player(new User("everest@oreizy.com", "blah2", true), 2);

        g2p1.AddCardToHand(new Card("blue", 1));
        g2p1.AddCardToHand(new Card("orange", 4));
        g2p1.AddCardToHand(new Card("yellow", 2));

        g2p1.ReceiveHint(new Hint("blue", "0", "1"));
        g2p1.ReceiveHint(new Hint("4", "0", "1"));

        g2p2.AddCardToHand(new Card("red", 5));
        g2p2.AddCardToHand(new Card("purple", 3));
        g2p2.AddCardToHand(new Card("blue", 1));

        ArrayList<Player> g2testPlayers = new ArrayList<>();
        g2testPlayers.add(g2p1);
        g2testPlayers.add(g2p2);
        g2testPlayers.add(p3);
        g2testPlayers.add(p4);

        GameData testGD2 = new GameData(g2testPlayers, 5, 30,
                "everest username testing", 1);
        GameControl testGC2 = new GameControl(testGD2);
        gameControls.add(testGC2);

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
                User user = new User("GenericUserName", "GenericUserID", false);
                ctx = new Context(user);
                System.out.println("context=" + ctx);
                System.out.println(user);
                map.put(tabid, ctx);
            }

            return ctx.toString();
        });

        get("/lobby-games", "application/json", (req, res) -> {
            return gameControls;
        }, new JSONRT());

        get("/update", "application/json", (req, res) -> {
            String gameID = req.queryParams("gid");
            System.out.println("update: Update requested by " + req.ip() + " for game "
                    + gameID);

            if (gameID.equals("")) { // return all games
                System.out.println("update: returning all games");
                return gameControls;
            } else if (Integer.parseInt(gameID) < 0) { // get IDs
                System.out.println("update: parsed -1, gathering user info");
                User reqUser = getContext(req).user;
                
                // TODO: TESTING, NEED TO CHANGE WHEN LOGIN IMPLEMENTED
                // reqUser = testPlayers.get(0).GetUser();
                // reqUser.SetInGameID(1);
                
                // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                
                System.out.println("update: Look for player " + reqUser.GetID() + " from game");
                
                for(GameControl game 
                        : gameControls) {               
                    for(Player player 
                            : game.getGameData().getPlayers()) {

                        System.out.println(reqUser.GetID() + " == " + player.myUser.GetID());
                        
                        if (player.myUser.GetID().equals(reqUser.GetID())) {
                            System.out.println("update: Grabed player " + player.myUser.GetID() + " from game");
                            reqUser.SetAttachedPlayerID(player.myID);

                            return reqUser;
                        }
                    }
                }
                                
                String errMsg = "Failed to grab user " + reqUser + " in game " + reqUser.GetInGameID();
                System.out.println(errMsg);
                return errMsg;
            } else { // get a specific game
                int gameID_int = Integer.parseInt(gameID);
                System.out.println("update: returning game data for game "+gameID_int);
                GameControl game;
                try {
                    game = gameControls.get(gameID_int);
                    GameData gameData = game.getGameData();

                    return gameData;
                } catch (Exception e) {
                    return "user " + getContext(req).user
                            + " is not a player in this game!";
                }
            }
        }, new JSONRT());

        get("/turn", (req, res) -> {
            String turnJSON = req.queryParams("turn");
            String cardJSON = req.queryParams("card");

            Turn turn = JSONRT.gson.fromJson(turnJSON, Turn.class);
            Card card = JSONRT.gson.fromJson(turnJSON, Card.class);

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

        put("/join_game", (req, res) -> {
            addToLobby(req);

            // Get user ID and requested game ID
            String userID = req.queryParams("userid");
            int gameID = Integer.parseInt(req.queryParams("gameid"));
            
            System.out.println("join_game: userID = " + userID);
            System.out.println("join_game: gameID = " + gameID);
            
            System.out.println("join_game: Find this user in the lobby!");

            for (User user
                    : lobbyUsers) { // Find this user in the lobby
                
                if (user.GetID().equals(userID)) {
                    System.out.println("join_game: Found user " + user.GetID());
                    for (GameControl game
                            : gameControls) { // Find this game

                        GameData gameData = game.getGameData();
                        System.out.println("join_game: game id is = " + gameData.getid());
                        
                        if (gameID == gameData.getid()) {
                            System.out.println("join_game: Found game " + gameID);
                            
                            gameData.removeComputerPlayer();

                            if (gameData.getPlayersSize() < 6) {
                                System.out.println("join_game: Adding user to the game");
                                
                                user.SetInGameID(gameData.getid());

                                gameData.insertPlayer(new Player(user));                                
                                
                                lobbyUsers.remove(user); // Remove user from lobby
                                                                                           
                                user.SetInGameID(gameID);

                                System.out.println("join_game: Added user to the game");

                                return "Entered user " + userID + " into game "
                                        + gameID;
                            } else {
                                System.out.println("join_game: Game is full");
                                return "Game " + gameID + " at max capacity";
                            }
                        }
                    }
                    return "Could not find game " + gameID;
                }
            }
            return "Could not find user " + userID;
        });
        
        put("/enter_game", (req, res) -> {
            // Get user ID and requested game ID
            String userID = req.queryParams("usr_id");
            String gameID = req.queryParams("game_id");

            System.out.println("Find this user in the lobby!");

            for (User user
                    : lobbyUsers) { // Find this user in the lobby
                
                if (user.GetID().equals(userID)) {
                    for (GameControl game
                            : gameControls) { // Find this game
                        if (gameID.equals(game.getGameData().getid())) {
                            if (players.size() < 6) {
                                user.SetInGameID(game.getGameData().getid());
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
            String gameIdString = req.queryParams("gid");

            Hint givenHint = JSONRT.gson.fromJson(hintParam, Hint.class);
            System.out.println("Received Hint:");
            System.out.println("\tTo:\t\t" + givenHint.playerToId);
            System.out.println("\tIs Color:\t" + givenHint.isColor);
            System.out.println("\tContent:\t" + givenHint.hintContent);

            Player target;

            try {
                final GameData targetGameData
                        = gameControls.get(Integer.parseInt(gameIdString)).
                                getGameData();
                target = targetGameData.getPlayerAtId(Integer.parseInt(
                        givenHint.playerToId));

                if (target != null) {
                    User playerUser = target.GetUser();

                    // Validate Hint
                    boolean validHint = false;
                    for (Card card
                            : target.GetHand().getCards()) {
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
                        // set the from ID based on context
                        for (Player player
                                : targetGameData.getPlayers()) {
                            if (player.GetUser().equals(getContext(req).user)) {
                                givenHint.playerFromId = Integer.toString(
                                        player.myID);
                                target.ReceiveHint(givenHint);
                                final String msg
                                        = "Gave hint to user " + playerUser.
                                                GetID();
                                System.out.println(msg);
                                return msg;
                            }
                        }
                        return "User " + getContext(req).user
                                + " is not in game "
                                + gameIdString + ". Can't give hint!";
                    } else {
                        return "Hint to player " + target.myID
                                + " was invalid";
                    }
                } else {
                    final String errMsg
                            = "Target player " + givenHint.playerToId
                            + " not found!";
                    System.out.println(errMsg);
                    return errMsg;
                }
            } catch (Exception e) {
                String errMsg = "Invalid hint params!: " + e.toString();
                System.out.println(errMsg);
                return errMsg;
            }
        });

        put("/discard", "application/json", (req, res) -> {
            System.out.println("Discarding...");

            int gameId = Integer.parseInt(req.queryParams("game_id"));
            int playerId = Integer.parseInt(req.queryParams("player_id"));

            String toDiscard = req.queryParams("to_discard");
            Card discard = JSONRT.gson.fromJson(toDiscard, Card.class);

            GameControl game = gameControls.get(gameId);
            Player player = game.getGameData().getPlayerAtId(playerId);
            Hand playerHand = player.GetHand();

            for (Card card
                    : playerHand.getCards()) {
                if (card.color.equals(discard.color) && card.number
                        == discard.number) {
                    return playerHand.discard(card, game.getGameData());
                }
            }

            return "Could not find either: Game " + gameId + " | Player @ Id "
                    + playerId + " | Card " + toDiscard;
        }, new JSONRT());
    }

    public static void addToLobby(Request req) {
        String userID = req.queryParams("userid");
        String username = req.queryParams("username");

        for(User user
                : lobbyUsers) {            
            if (user.GetID().equals(userID)) {
                return;
            }                    
        }

        lobbyUsers.add(new User(username, userID, false));
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
