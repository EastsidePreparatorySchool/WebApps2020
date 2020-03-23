/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

import java.util.ArrayList;
import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.*;

public class Main {
    public static ArrayList<String> msgs = new ArrayList<>(); 
    public static void main(String[] args) {
       // msgs.add("hi");
        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("/");
        
        put("/send", (req, res) -> {
            System.out.println("Send message requested");
            
            String msg = req.queryParams("msg");
            synchronized(msgs) {
               msgs.add(msg);
               msgs.add(req.session().id());
           }
            return HttpStatus.ACCEPTED_202;
        });
        
        get("/get", (req, res) -> { 
            return msgs.toString();
        });
        
        get("/login_user", (req, res) -> { 
            String username = req.queryParams("username");
            login(req, username);
            return msgs.toString();
        });
    }
    
    spark.Session getSession(spark.Request req) {
            spark.Session s = req.session(true); // true means if there is none, make one
            return s;
    }
    String user (spark.Request req) {
        String user = getSession(req).attribute("user");
        if (user == null) {
            user = "unknown"; // default name
        }
        return user;
    }
    
    //logging in
    void login(spark.Request req, String username) {
        getSession(req).attribute("user", username);
    }
}
