/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import org.eastsideprep.hanabiserver.*;

/**
 *
 * @author eoreizy
 */
public interface GameInterface {
    
    public int getId();
    
    public ArrayList<Player> getPlayers();
    
    public void AddPlayer(Player player);
    
    public Player RemovePlayer(int id);
    
    public Player getPlayerAtId(int id);
    
    public Hand getPlayerHandAtId(int id);
    
    public Deck getDeck();
    
    public CardInterface getDeckCardAtId(int id);
    public PlayedCards getPlayedCardPile(String playedCardPileColor);
    
    public Discard getDiscardPile();
    
    public ArrayList<Hint> getAllSentHints();
    
    public int getMaxCardsInHand();
    
    public int getRemainingStrikes();
}
