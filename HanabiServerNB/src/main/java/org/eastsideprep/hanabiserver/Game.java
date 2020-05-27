/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import java.util.HashMap;
import org.eastsideprep.hanabiserver.interfaces.GameInterface;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author eoreizy
 */
public class Game implements GameInterface {

    private ArrayList<Player> players;
    
    private int remainingStrikes;
    
//    private static int GameIdSettingValue=1; 
    private static AtomicInteger GameIdSettingValue2=new AtomicInteger(Integer.MIN_VALUE);
    
    private String name;
    
    private int gameID;
    
 //   private ArrayList<Card> deck; // Can be an instance of the Deck class
    private Deck deck; // Can be an instance of the Deck class
    
    private HashMap<String, PlayedCards> playedCardPiles;
    
    private Discard discardPile;
    
    Game(String nm){ //whenever you call game, synchronize
        name=nm;
        gameID=GameIdSettingValue2.getAndDecrement();

        // gameID=GameIdSettingValue;
        // GameIdSettingValue+=1;
    }
    
    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public ArrayList<Card> getDeck() {
        return (ArrayList<Card>) deck.getCards();
    }

    @Override
    public Player getPlayerAtId(int id) {
        return players.get(id);
    }

    @Override
    public Hand getPlayerHandAtId(int id) {
        return players.get(id).GetHand();
    }

    @Override
    public Card getDeckCardAtId(int id) {
        return deck.getCards().get(id);
    }

    @Override
    public int getRemainingStrikes() {
        return remainingStrikes;
    }

    @Override
    public ArrayList<Hint> getAllSentHints() {
        ArrayList<Hint> hints = new ArrayList<Hint>();
        for (Player p : players){
            hints.addAll(p.GetReceivedHints());
        }
        return hints;
    }
    
    public int getid(){
        return gameID;
    }
    
    public String getname(){
        return name;
    }
    
    
    @Override
    public PlayedCards getPlayedCardPile(String playedCardPileColor) {
        return playedCardPiles.get(playedCardPileColor);
    }

    @Override
    public Discard getDiscardPile() {
        return this.discardPile;
    }
    
    @Override
    public int getMaxCardsInHand() {
        if (players.size() <= 3) {
            return 5;
        } else if (players.size() <= 5) {
            return 4;
        } else {
            return 3;
        }
    }
    
    Game(ArrayList<Player> players, Deck deck, HashMap<String, PlayedCards> playedCards, Discard discard) {
        this.players = players;
        this.deck = deck;
        this.discardPile = discard;
        this.playedCardPiles = playedCards;
        
        this.remainingStrikes = 3;
        
        gameID=GameIdSettingValue2.getAndDecrement();
    }
    
    Game(ArrayList<Player> players, Deck deck, HashMap<String, PlayedCards> playedCards, Discard discard, String name) {
        this.players = players;
        this.deck = deck;
        this.discardPile = discard;
        this.playedCardPiles = playedCards;
        
        this.remainingStrikes = 3;
        this.name=name;
        gameID=GameIdSettingValue2.getAndDecrement();
    }
}
