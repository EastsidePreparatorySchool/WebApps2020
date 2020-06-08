/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.GameControlInterface;
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

    public Card discard(int pos, GameData gd) {
        Card card = cards.get(pos);
        
        return discard(card, gd);
    }

    public Card discard(Card card, GameData gd) {
        this.addCard(gd.getDeck().draw());
        return GameControlInterface.moveCard(card, this, gd.getDiscardPile());
    }
    
    public boolean play(int pos, GameData gd, String color){
        this.addCard(gd.getDeck().draw());
        int temp=gd.getPlayedCardPile(color).getCards().size()-1;
        if(cards.get(pos).number==temp+1){
            GameControlInterface.moveCard(cards.get(pos), this, gd.getPlayedCardPile(color));
            return true;
        }
        return false;
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
