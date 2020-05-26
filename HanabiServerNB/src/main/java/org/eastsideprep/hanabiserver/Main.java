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
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.CardSpotInterface;
import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.*;

// optional chat feature in lobby (Aybala) - msg class
class Message {

    public String username;
    public String msg;
    public String msgtime;
}

//HANABI SERVER NB
public class Main {

    // optional chat feature
    public static ArrayList<Message> msgs = new ArrayList<>();

    final static String[] CARD_COLORS = new String[]{"Purple", "Green", "Yellow", "Blue", "Red"};
    final static int CARD_NUMBERS = 5;
    final static int CARD_DUPLICATES = 3;

    static ArrayList<Game> games = new ArrayList<>();
    static GameControl gameControl;

    static ArrayList<Player> players = new ArrayList<>();

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

        gameControl = new GameControl();

        // optional chat feature in lobby (Aybala)
        put("/send", (req, res) -> {
            System.out.println("Send message requested");

            String msg = req.queryParams("msg");
            Message newMessage = new Message();
            newMessage.username = user(req);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            newMessage.msgtime = dtf.format(LocalDateTime.now());
            newMessage.msg = msg;

            synchronized (msgs) {
                msgs.add(newMessage);
                System.out.println(msgs.toString());
            }

            return HttpStatus.ACCEPTED_202; // returning that our request was accepted
        });

        get("/get", "application/json", (req, res) -> {
            JSONRT rt = new JSONRT(); // created a response transformer object
            synchronized (msgs) {
                String result = rt.render(msgs); // rendered java objects into JSON string

                return result;
            }
        }, new JSONRT());

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

        Game game = new Game(players, deck, playedCards, discards);
        games.add(game); // "players" here needs to become a subset

        players.forEach((player) -> {
            for (int i = 0; i < game.getMaxCardsInHand(); i++) {
                player.AddCardToHand(deck.draw());
            }
        });
    }
}
