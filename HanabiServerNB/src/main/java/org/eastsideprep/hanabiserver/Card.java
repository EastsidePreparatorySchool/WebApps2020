/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import org.eastsideprep.hanabiserver.interfaces.CardInterface;

/**
 *
 * @author eoreizy
 */
public class Card implements CardInterface {
    private String color; 
    private int number; 
    private boolean played;
    private boolean discarded;    
    
    //do we still need booleans for color_revealed and/or number_revealed?

    @Override
    public void isDicarded(boolean isdicarded) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void isPlayer(boolean isplayed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}