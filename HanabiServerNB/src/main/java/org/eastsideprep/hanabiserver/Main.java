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
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import static spark.Spark.*;

//HANABI SERVER NB

public class Main {
    
    final static String[] CARD_COLORS = new String[] {"Purple", "Green", "Yellow", "Blue", "Red"};
    final static int CARD_NUMBERS = 5;
    final static int CARD_DUPLICATES = 3;
    
    static Game game;
    static GameControl gameControl;
    
    static ArrayList<Player> players = new ArrayList<>();
    static int maxCardsInHand;
    
    static Deck deck;
    
    
    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("static");

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
            }
            Context ctx = map.get(tabid);

            // no context? no problem.
            if (ctx == null) {
                ctx = new Context();
                map.put(tabid, ctx);
            }
            
            return ctx.toString();
        });
        
        put("/update", (req, res) -> {
            return "/update route";
        });
        
        put("/turn", (req, res) -> {
            return "/turn route";
        });
        
        StartGame();
    }
    
    public static void StartGame() {
        gameControl = new GameControl();
        
        ArrayList<Card> tempDeck = new ArrayList<>();
        for (int cardNumber = 1; cardNumber <= CARD_NUMBERS; cardNumber++) {
            for (String cardColor : CARD_COLORS) {
                for (int i = 0; i < CARD_DUPLICATES; i++) {
                    tempDeck.add(new Card(cardColor, cardNumber));
                }
            }
        }
        deck = new Deck(tempDeck);
        
        gameControl.shuffle(deck);
        
        players.forEach((player) -> {
            for (int i = 0; i < maxCardsInHand; i++) {
                player.AddCardToHand(deck.draw());
            }
        });
        
        game = new Game(players, deck);
    }
}
