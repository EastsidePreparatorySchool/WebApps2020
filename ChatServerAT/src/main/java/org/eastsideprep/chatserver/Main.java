/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.*;

class Message {
    public String username;
    public String msg;
}

public class Main {

  public static ArrayList<Message> msgs = new ArrayList<>();
  
    public static void main(String[] args) {
        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("/");

        put("/send", (req, res) -> {
            System.out.println("Send message requested");

            String msg = req.queryParams("msg"); 
            Message newMessage = new Message();
            newMessage.username = user(req);
            newMessage.msg = msg;
                
            synchronized (msgs) {
                msgs.add(newMessage);
                //msgs.add(user(req)+"~ "+msg); //combine username
                System.out.println(msgs.toString());
            }

            return HttpStatus.ACCEPTED_202; // returning that our request was accepted
        });     
              
        get("/get", "application/json", (req, res) -> {   
            JSONRT rt = new JSONRT();
            synchronized (msgs) {
                String result = rt.render(msgs);
                
                return result;
            }
        }, new JSONRT());
        
        get("/login_user", (req, res) -> {
            String user = req.queryParams("username");

            getSession(req).attribute("username", user);
            
            System.out.print(user);
            return user;
        });
    }

    static spark.Session getSession(spark.Request req) {
        spark.Session s = req.session(true); // true means if there is none, make one
        return s;
    }

    static String user(spark.Request req) {
        String user = getSession(req).attribute("username");
        if (user == null) {
            user = "unknown"; // default name
        }
        return user;
    }
}
