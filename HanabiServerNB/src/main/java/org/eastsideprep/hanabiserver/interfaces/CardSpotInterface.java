/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.Card;

/**
 *
 * @author mgoetzmann
 */
public interface CardSpotInterface {
    
    public String getName();
    
    public ArrayList<Card> getCards();
    
//    public CardInterface remove(); //All CardSpots don't share these
//    
//    public void add();
    
//      
}
