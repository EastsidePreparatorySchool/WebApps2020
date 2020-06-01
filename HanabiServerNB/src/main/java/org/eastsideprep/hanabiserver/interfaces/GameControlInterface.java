/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author etardif
 */
public interface GameControlInterface {
    
    // Shuffle the cards currently in a spot
    public static void shuffle(CardSpotInterface spot) {
        Collections.shuffle(spot.getCards());
    }; 
    
    // Move a card from one spot to another
    public void moveCard(CardInterface card, CardSpotInterface spotFrom, CardSpotInterface spotTo);
    
}
