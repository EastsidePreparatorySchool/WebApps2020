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
public class Hand implements HandInterface {

    private ArrayList<Card> cards;
    private String Name; //letting it be defined in constructor 
    //bec we have multiple hands

    Hand(String name) {
        Name = Name;
        cards = new ArrayList<>();
    }
    
    Hand() {
        cards = new ArrayList<>();
        Name = "not set";
    }

    @Override
    public void draw(Card Card) {
        cards.add(Card);   //I assumed that this didn't include discarding
    }

    public CardInterface discard(int pos) {
        CardInterface x = cards.get(pos);
        cards.remove(x);

        //need to change member variable of card, discarded to true
        return x;
    }

    public CardInterface discard(CardInterface card) {
        boolean x = cards.remove(card);
        if (x) {return card;}

        return null;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public void addCard(Card c){
        cards.add(c);
    }
}
