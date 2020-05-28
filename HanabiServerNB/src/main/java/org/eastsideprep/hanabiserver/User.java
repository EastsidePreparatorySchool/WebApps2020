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
import org.eastsideprep.hanabiserver.*;
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

    String name;
    String usernameForMsg;

    // optional chat feature
    public static ArrayList<Message> msgs = new ArrayList<>();

    public static void setup(String[] args) {
        System.out.println("it's working");
        staticFiles.location("static");
        get("getheaders", (req, res) -> getHeaders(req));
        get("loginextra", (req, res) -> loginextra(req, res));
        get("login", (req, res) -> login(req, res));

        get("/headers", (req, res) -> {
            String result = "";

            for (String s : req.headers()) {
                result += s + ":" + req.headers(s) + "<br>";
            }

            return result;
        });

        get("/load", (Request req, Response res) -> {
            // Open new, independent tab
            spark.Session s = req.session();

            // if the session is new, make sure it has a context map
            if (s.isNew()) {
                s.attribute("map", new HashMap<String, org.eastsideprep.hanabiserver.Context>());
            }

            // now we can safely access the context map whether the session is new or not
            HashMap<String, org.eastsideprep.hanabiserver.Context> map = s.attribute("map");
            System.out.println("map =" + map);

            // find the context that goes with the tab
            String tabid = req.headers("tabid");
            if (tabid == null) {
                tabid = "default";
                System.out.println(tabid);
            }
            org.eastsideprep.hanabiserver.Context ctx = map.get(tabid);
            System.out.println("tabid =" + tabid);

            // no context? no problem.
            if (ctx == null) {
                User user = new User();
                ctx = new org.eastsideprep.hanabiserver.Context(user);
                System.out.println("context=" + ctx);
                System.out.println(user);
                map.put(tabid, ctx);
            }
            System.out.println(tabid);
            String username = ctx.user.getName();
            System.out.println("user=" + username);

            if (username == null) {
                username = "unknown"; // default name
            }
            return ctx.toString();
        });
        
        // optional chat feature in lobby (Aybala)
        put("/send", (req, res) -> {
            System.out.println("Send message requested JAAAAAAAAAA");

            String msg = req.queryParams("msg");
            
            Message newMessage = new Message();

            newMessage.username = "Aybala"; 

            System.out.println(newMessage.username);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            newMessage.msgtime = dtf.format(LocalDateTime.now());
            newMessage.msg = msg;

            synchronized (msgs) {
                msgs.add(newMessage);
                System.out.println(msgs.toString());
            }

            return "hi";
        });

        get("/get", "application/json", (req, res) -> {
            JSONRT rt = new JSONRT(); // created a response transformer object
            synchronized (msgs) {
                String result = rt.render(msgs); // rendered java objects into JSON string

                return result;
            }
        }, new JSONRT());

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String loginextra(Request req, Response res) {
        String red = "https://epsauth.azurewebsites.net/login?url=http://localhost:80/login&loginparam=username&passthroughparam=tabid&passthrough="
                + req.queryParams("tabid");
        // logger.info("ChatServer: redirecting: " + red);
        res.redirect(red, 302);
        return ":0";
    }

    private static String login(Request req, Response res) {
        String red = "http://localhost:80";
        //logger.info("ChatServer: redirecting: " + red);
        res.redirect(red, 302);
        String username = req.queryParams("username");
        String tabid = req.queryParams("tabid");
        HashMap<String, org.eastsideprep.hanabiserver.Context> map = getSession(req).attribute("map");
        Context ctx = map.get(tabid);
        String eachUser = username + tabid;
        System.out.println("username in login=" + username);
        System.out.println("tabid in login=" + tabid);
        System.out.println("ctx in login=" + ctx);
        ctx.user.setName(eachUser);
        System.out.println("eachUser=" + eachUser);
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
            user = "unknown"; // default name
        }
        return user;
        //how to merge together
    }
     */
    static spark.Session getSession(spark.Request req) {
        spark.Session s = req.session(true); // true means if there is none, make one
        return s;
    }

    private static String getHeaders(Request req) {
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

}
