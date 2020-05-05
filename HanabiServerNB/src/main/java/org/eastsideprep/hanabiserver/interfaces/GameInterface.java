/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.*;

/**
 *
 * @author eoreizy
 */
public interface GameInterface {
    
    public ArrayList<Player> getPlayers();
    
    public Player getPlayerAtId(int id);
    
    public Player getPlayerHandAtId(int id);
    
    public ArrayList<Card> getDeck();
    
    public Card getDeckCardAtId(int id);
}
