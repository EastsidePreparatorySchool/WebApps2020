/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.CardSpotInterface;

/**
 *
 * @author mgoetzmann
 */
public class Discard implements CardSpotInterface {

    private final String name = "Discard Pile"; //figure we'll have only 1
    
    private ArrayList<Card> cards = new ArrayList<>();
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public void add(Card card){
        cards.add(card);
    }
}
