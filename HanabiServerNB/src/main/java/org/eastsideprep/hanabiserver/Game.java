/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.interfaces.GameInterface;

/**
 *
 * @author eoreizy
 */
public class Game implements GameInterface {

    private ArrayList<Player> players;
    
    private int remainingStrikes;
    
    private Deck deck; // Can be an instance of the Deck class
    
    private ArrayList<Discard> discardPiles;
    
    private PlayedCards playedCards;
    
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
    
    Game(ArrayList<Player> players, Deck deck, ArrayList<Discard> discard, PlayedCards playedCards) {
        this.players = players;
        this.deck = deck;
        this.discardPiles = discard;
        this.playedCards = playedCards;
        
        this.remainingStrikes = 3;
    }
}
