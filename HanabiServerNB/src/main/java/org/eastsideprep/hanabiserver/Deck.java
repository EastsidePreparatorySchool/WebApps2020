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
public class Deck implements CardSpotInterface{
    
    private final String name = "Deck";
    
    private ArrayList<CardInterface> cards;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<CardInterface> getCards() {
        return cards;
    }
    
    public Deck(ArrayList<CardInterface> cards){
        this.cards = cards;
    }
}
