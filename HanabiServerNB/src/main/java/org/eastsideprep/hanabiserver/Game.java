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
    
    private ArrayList<Card> deck;
    
    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public ArrayList<Card> getDeck() {
        return deck;
    }

    @Override
    public Player getPlayerAtId(int id) {
        return players.get(id);
    }

    @Override
    public Player getPlayerHandAtId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //Until I see whatever the getHand() function on a Player is.
    }

    @Override
    public Card getDeckCardAtId(int id) {
        return deck.get(id);
    }
    
}
