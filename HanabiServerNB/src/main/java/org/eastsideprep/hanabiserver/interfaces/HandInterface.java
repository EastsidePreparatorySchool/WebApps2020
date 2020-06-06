/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

import org.eastsideprep.hanabiserver.Card;

/**
 *
 * @author etardif
 */
public interface HandInterface extends CardSpotInterface {
    
        
    // "ArrayList<CardInterface> cards" is inferred from CardSpotInterface
    
    public void draw(Card Card); 
    
    public CardInterface discard(int pos);
    
}
