/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.GameInterface;

/**
 * 
 * @author eoreizy
 */
public class GameData implements GameInterface {

    private int gameId;
    
    private String name;
    
    private ArrayList<Player> players;
    
    private int remainingStrikes;
    
    private Deck deck; // Can be an instance of the Deck class
    
    public GameData(ArrayList<Player> players, int startingStrikes, int deckVolume, String name, int gameId){
        this.players = players;
        this.remainingStrikes = startingStrikes;
        this.deck = new Deck(new ArrayList<CardInterface>());
        this.name = name;
        this.gameId = gameId;
        //do the stuff to fill the deck//
    }
    
    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public Deck getDeck() {
        return deck;
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
        ArrayList<Hint> hints = new ArrayList<Hint>();
        for (Player p : players){
            hints.addAll(p.GetReceivedHints());
        }
        return hints;
    }
    
}
