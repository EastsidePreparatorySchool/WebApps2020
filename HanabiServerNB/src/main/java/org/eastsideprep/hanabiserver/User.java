/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.eastsideprep.hanabiserver.interfaces.UserInterface;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

/**
 *
 * @author kuberti
 */
// optional chat feature in lobby (Aybala) - msg class
class Message {

    public String username;
    public String msg;
    public String msgtime;
}

public class User implements UserInterface {

    String username;
    String tabid;

    public boolean CompPlayer;
    private String ID;
    private int InGameID;
    private int attachedPlayerID;

    public User(String name, String id, boolean compPlayer) {
        username = name;
        ID = id;
        CompPlayer = compPlayer;
    }

    // optional chat feature
    public static ArrayList<Message> msgs = new ArrayList<>();

    

    

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
    
    public static String getUsername(Context ctx) {
        if (ctx.user != null) {
            return User.getUsername(ctx);
        }
        
        return "<Username: User not set yet>";
    }

    public void setTabId(String tabid) {
        this.tabid = tabid;
    }

    public String getTabId() {
        return tabid;
    }
    
    public static String getTabId(Context ctx) {
        if (ctx.user != null) {
            return ctx.user.getTabId();
        }
        
        return "<TabID: User not set yet>";
    }
    
    public void setID(String userID) {
        this.ID = userID;
    }

    public String getID() {
        return ID;
    }

    public static String loginextra(Request req, Response res) {
        String red = "https://epsauth.azurewebsites.net/login?url=http://localhost:80/login&loginparam=username&passthroughparam=tabid&passthrough="
                + req.queryParams("tabid");
        // logger.info("ChatServer: redirecting: " + red);
        res.redirect(red, 302);
        return ":0";
    }

    public static String login(Request req, Response res) {
        String red = "http://localhost:80/index.html";
        //logger.info("ChatServer: redirecting: " + red);
        res.redirect(red, 302);
        String username = req.queryParams("username");
        String tabid = req.queryParams("tabid");
        HashMap<String, org.eastsideprep.hanabiserver.Context> map = getSession(req).attribute("map");
        Context ctx = map.get(tabid);
        String userID = username + tabid;
        
        User newUser = new User(username, userID, false);
        ctx.user = newUser;
        
        ctx.user.setTabId(tabid);
//        ctx.user.setUsername(username);
//        ctx.user.setID(userID);
        
        System.out.println("username in login=" + username);
        System.out.println("tabid in login=" + tabid);
        System.out.println("userID=" + userID);
        System.out.println("ctx in login=" + ctx);
        return username;
    }


    /*
     static String user(spark.Request req, Response res) {
         HashMap<String, org.eastsideprep.hanabiserver.Context> map = getSession(req).attribute("map");
          String username = req.queryParams("username");
         String tabid = req.queryParams("tabid");
         if (map == null){
             return null;
         }
        Context ctx = map.get(tabid);
         if (ctx == null) {
            return null;
        }
         
       
        
        String user = ctx.user.getName();
         System.out.println("user=" +user);
         
        if (user == null) {
            user = "unknown"; // default Name
        }
        return user;
        //how to merge together
    }
     */
    static spark.Session getSession(spark.Request req) {
        spark.Session s = req.session(true); // true means if there is none, make one
        return s;
    }

    public static String getHeaders(Request req) {
        String result = "";

        for (String s : req.headers()) {
            result += s + ":" + req.headers(s) + "<br>";

        }
        return result;
    }

    @Override
    public void joinGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetInGameID(int gameID) {
        InGameID = gameID;
    }

    @Override
    public String GetName() {
        return username;
    }

    @Override
    public String GetID() {
        return ID;
    }

    @Override
    public int GetInGameID() {
        return InGameID;
    }

    @Override
    public void SetAttachedPlayerID(int playerID) {
        attachedPlayerID = playerID;
    }

    @Override
    public int GetAttachedPlayerID() {
        return attachedPlayerID;
    }

}
