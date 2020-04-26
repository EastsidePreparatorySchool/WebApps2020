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

public class Main {
public static ArrayList<String> msgs = new ArrayList<>();

  public static Object getNewMessages(spark.Request req, spark.Response res) {
        List<String> myMessages;
        
        synchronized (msgs) {
            myMessages = msgs;
        }
        return myMessages;
    }
  
    public static void main(String[] args) {
        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("/");

        put("/send", (req, res) -> {
            System.out.println("Send message requested");

            String msg = req.queryParams("msg"); 
            synchronized (msgs) {
                msgs.add(user(req)+"~ "+msg); //combine username
            }
            System.out.println(msgs.toString());

            return HttpStatus.ACCEPTED_202; // returning that our request was accepted
        });
              
        
     //   get("/protected/getnewmessages", "application/json", (req, res) ->  getNewMessages(req, res), new JSONRT());      
              
        get("/get", "application/json", (req, res) -> {   
            JSONRT rt = new JSONRT();
            //String s = "";
            synchronized (msgs) {
                //for (int i = 0; i < msgs.size(); i++){
                //    s+=(msgs.get(i)+"\n"); // making array into strings
                //}                
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
