/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

import java.util.ArrayList;
import org.eastsideprep.hanabiserver.*;

/**
 *
 * @author etardif
 */
public interface PlayerInterface {

    // Actions
    public void SendHint(Hint hint, Player toPlayer);

    public void SendDiscard(Card card);

    public void SendMove(Card card);
    
    public void UpdateSelfHints(); // refresh known hints to check if they still apply

    // Input
    public void ReceiveHint(Hint hint);
    
    public void AddCardToHand(Card card);
    
    // ID and properties
    public String GetUsername();

    public Hand GetHand();
    
    public ArrayList<Hint> GetReceivedHints(); // grab what is known about the cards
}
