/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;


import java.util.ArrayList;
import org.eastsideprep.hanabiserver.*;
import spark.Request;
import spark.Response;
/**
 *
 * @author kuberti
 */
public interface UserInterface {
    public static String login(Request req, Response res){
        return "ok";
    }
    
    public void joinGame(); 
    
    public void SetInGameID(String gameID);
    
    public String GetName();
    public String GetID();
    public String GetInGameID();    
}
