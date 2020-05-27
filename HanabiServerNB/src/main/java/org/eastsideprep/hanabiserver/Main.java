/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static spark.Spark.*;

//HANABI SERVER NB
public class Main {

    static ArrayList<GameControl> games;
    
    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("static");

        games = new ArrayList<>();

        //Making a test GameControl object
        ArrayList<Player> testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Windows"));
        testPlayers.add(new Player("MacOS"));
        testPlayers.add(new Player("Linux"));
        GameData testGD = new GameData(testPlayers, 5, 30, "a game", 0);
        GameControl testGC = new GameControl(testGD);
        games.add(testGC);

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
            }
            Context ctx = map.get(tabid);

            // no context? no problem.
            if (ctx == null) {
                ctx = new Context();
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
    }
}
