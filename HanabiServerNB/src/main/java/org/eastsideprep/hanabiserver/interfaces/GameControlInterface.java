/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.eastsideprep.hanabiserver.Card;

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
    public static Card moveCard(Card card, CardSpotInterface spotFrom, CardSpotInterface spotTo) {
        if (spotFrom.getCards().remove(card)) {
            spotTo.getCards().add(card);
            return card;
        }
        return null;
    };
    
}
