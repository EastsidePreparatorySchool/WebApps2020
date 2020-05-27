/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

/**
 *
 * @author mgoetzmann
 */
public class Context {
    public User user;
    public Player player;
    private Game game;
    
    public void enterGame(Game g) {
        this.game = g;
    }
    
    public Game leaveGame() {
        Game temp = this.game;
        this.game = null;
        
        return temp;
    }
    
    public Game getGame() {
        return this.game;
    }
    
    public Context(User user){
        this.user = user;
    }
    
}
