/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

/**
 *
 * @author etardif
 */
public interface HandInterface extends CardSpotInterface {
    
        
    // "ArrayList<CardInterface> cards" is inferred from CardSpotInterface
    
    public void draw(CardInterface Card); 
    
    public CardInterface discard(int pos);
    
}
