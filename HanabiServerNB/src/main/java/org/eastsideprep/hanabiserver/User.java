/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

/**
 *
 * @author kyang
 */
public class User {
    private final String StringName;
    private final String ID;
    private String InGameID;
    
    public User(String name, String id) {
        StringName = name;
        ID = id;
    }
    
    public void SetInGame(String gameID) {
        InGameID = gameID;
    }
    
    
    public String GetStringName() {
        return StringName;
    }
    public String GetID() {
        return ID;
    }
    public String GetInGameID() {
        return InGameID;
    }
}
