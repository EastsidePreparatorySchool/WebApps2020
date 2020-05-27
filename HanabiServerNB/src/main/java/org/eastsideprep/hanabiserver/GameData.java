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
    
    public int debug = 0;
    
    private int id;

    private int gameId;
    
    private String name;
    
    private ArrayList<Player> players;
    
    private int remainingStrikes;
    
//    private static int GameIdSettingValue=1; 
    private static AtomicInteger GameIdSettingValue2=new AtomicInteger(Integer.MIN_VALUE);
    
    private String name;
    
    private int gameID;
    
 //   private ArrayList<Card> deck; // Can be an instance of the Deck class
    private Deck deck; // Can be an instance of the Deck class
    
    public GameData(ArrayList<Player> players, int startingStrikes, int deckVolume, String name, int gameId){
        this.players = players;
        this.remainingStrikes = startingStrikes;
        this.deck = new Deck(new ArrayList<CardInterface>());
        this.name = name;
        this.gameId = gameId;
        //do the stuff to fill the deck//
    }
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
    public Deck getDeck() {
        return deck;
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
    public CardInterface getDeckCardAtId(int id) {
        return deck.getCards().get(id);
    }

    @Override
    public int getRemainingStrikes() {
        return remainingStrikes;
    }

    @Override
    public ArrayList<Hint> getAllSentHints() {
        ArrayList<Hint> hints = new ArrayList<>();
        players.forEach((p) -> {
            hints.addAll(p.GetReceivedHints());
        });
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
    
    Game(int id, ArrayList<Player> players, Deck deck, HashMap<String, PlayedCards> playedCards, Discard discard) {
        this.id = id;
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

    @Override
    public void AddPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public Player RemovePlayer(int id) {
        return this.players.remove(id);
    }

    @Override
    public int getId() {
        return this.id;
    }
}
