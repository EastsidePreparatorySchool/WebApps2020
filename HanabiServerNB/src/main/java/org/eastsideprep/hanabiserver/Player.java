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
//    private String myUsername;
    private User myUser;
    private Hand myHand;
    private int myID;
    private int inGameID;
    //private int inGameID; // DEPRECIATED

    private ArrayList<Hint> myHints; // things I known about my cards
    
    public Player(User user) {
        myUser = user;
        myHand = new Hand();
    }

    public Player(String username){ // This functionality has been moved to the User class
        this.myUser.setUsername(username);
        this.myHints = new ArrayList<Hint>();
        this.myHand = new Hand();
    }
    
    public Player(User user, String username, int playerID) {
        myID = playerID;
        myUser = user;
        myUser.setUsername(username);
        this.myHints = new ArrayList<>();


        this.myHand = new Hand();


    }
    
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
        for (Hint myHint: myHints) {
            if (myHint.equals(hint)) {
                System.out.println("User "+myUser.GetID()+" already has this hint");
                break;
            }
        }
        myHints.add(hint);
    }

    @Override
    public void AddCardToHand(Card card) {
        myHand.addCard(card);
    }
    

//    @Override
//    public String GetUsername() {
//        return myUser.GetName();
//    }

    @Override
    public Hand GetHand() {
        return myHand;
    }

    @Override
    public ArrayList<Hint> GetReceivedHints() {
        return myHints;
    }

    @Override
    public User GetUser() {
        return myUser;
    }
    
}
