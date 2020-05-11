/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.HandInterface;


/**
 *
 * @author eoreizy
 */
public class Hand implements HandInterface{    
    private ArrayList<CardInterface> cards;
    private String Name; //letting it be defined in constructor 
                         //bec we have multiple hands
    
    Hand(String name){
        name=Name;
    }
    
    @Override
    public void draw(CardInterface Card) {
        cards.add(Card);   //I assumed that this didn't include discarding
    }
    
    public CardInterface discard(int pos) {
        CardInterface x= cards.get(pos);
        cards.remove(x);
        
        //need to change member variable of card, discarded to true
        return x;
    }
    
    @Override
    public String getName() {
        return Name;
    }

    @Override
    public ArrayList<CardInterface> getCards() {
        return cards;
    }
    
}
