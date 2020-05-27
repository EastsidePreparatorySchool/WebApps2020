/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.interfaces.PlayerInterface;

/**
 *
 * @author eoreizy
 */
public class Player implements PlayerInterface {

    // Private Variables
    private User user; // User object that holds session (and eventually server) saved data
    private String myUsername;
    private Hand myHand;
    private ArrayList<Hint> myHints; // things I known about my cards

    @Override
    public void SendHint(Hint hint, Player toPlayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendDiscard(Card card) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendMove(Card card) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateSelfHints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveHint(Hint hint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void AddCardToHand(Card card) {
        this.myHand.draw(card);
    }

    @Override
    public String GetUsername() {
        return this.myUsername;
    }

    @Override
    public Hand GetHand() {
        return this.myHand;
    }

    @Override
    public ArrayList<Hint> GetReceivedHints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User GetUserObject() {
        return this.user;
    }
    
    Player(User user, Hand hand) {
        this.user = user;
        this.myUsername = user.name;
        this.myHand = hand;
        this.myHints = new ArrayList<>();
    }
}
