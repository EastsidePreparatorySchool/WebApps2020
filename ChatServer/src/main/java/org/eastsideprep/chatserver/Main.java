/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

// @Author: Kenneth Y.
public class Main {

    static JSONRT gson = new JSONRT();

    public static final ArrayList<String> allMessagesArrayList
            = new ArrayList<>();

    public static void main(String[] args) {
        // tell spark where to find all the HTML and JS
        staticFiles.location("/");
        port(80);

        // Manually Populate
        allMessagesArrayList.add("Message 1");
        allMessagesArrayList.add("Second Message");
        allMessagesArrayList.add("Something else");

        // TODO: better handle displaying shown messages
        // get all new messages
        get("/update_messages", "application/json", (req, res) -> {
            System.out.println("Update messages requested: ");

            String msgs = "";

            synchronized (allMessagesArrayList) {
                int lastSeenIndex = getSeenIndex(req) + 1;
                final List<String> newMsgs
                        = allMessagesArrayList.subList(
                                lastSeenIndex, allMessagesArrayList.size());

                if (newMsgs.size() > 0) {
                    msgs = String.join("\n", newMsgs);
                    msgs += "\n";
                    setSeenAttribute(req, allMessagesArrayList.size() - 1);
                }
            }

            return new Message(msgs, getSession(req).attribute("user"));
        }, new JSONRT());

        get("/headers", (Request req, Response res) -> {
            System.out.println("Headers requested");
            String result = "";

            result = req.headers().stream().map((s) -> s + ":" + req.headers(s) + "<br>").reduce(result, String::concat);

            return result; // TODO: Print this to client HTML
        });

        // Send message
        put("/send_message", (req, res) -> {
            System.out.println("Send message requested");

            String msg = req.queryParams("msg");

            synchronized (allMessagesArrayList) {
                allMessagesArrayList.add("From User " + user(req) + " on "
                        + req.session().id() + ":");
                allMessagesArrayList.add(msg);
            }

//            HashMap<String, Context> cm = req.session().attribute("map");
//            cm.get(req.headers("tabid")).messagesSent++;
//            cm.get(req.headers("tabid")).messages.add(msg);
                       
            return msg;
        });

        // Login route
        put("/login_user", (req, res) -> {
            // For custom username
            System.out.println("Login user requested");

            String usernameJSON = req.queryParams("username");

            String username = gson.render(usernameJSON);

            login(req, username);

            return username;
        });
        
        get("/login_user", (req, res) -> {
            String result = req.headers("X-MS-CLIENT-PRINCIPAL-NAME");
            
            System.out.println("Login user requested");
            
            res.redirect("/", 302);
            
            login(req, "string");
            
            return "ok";
        });
        
        get("/session", (req, res) -> {
            spark.Session s = req.session();

            if (s.isNew()) {
                s.attribute("map", new HashMap<String, Context>());
            }

            HashMap<String, Context> map = s.attribute("map");

            String tabid = req.headers("tabid");
            if (tabid == null) {
                tabid = "default";
            }
            Context ctx = map.get(tabid);

            if (ctx == null) {
                ctx = new Context();
                map.put(tabid, ctx);
            }
            
            return ctx.toString(); // Return a hashcode that is arbitrary for our purposes
        });
        
//        get("/context", (req, res) -> {
//            System.out.println("Context requested");
//            HashMap<String, Context> cm = req.session().attribute("map");
//            
//            String contextStr = "";
//            
//            contextStr += cm.get(req.headers("tabid")).username +"\n";
//            contextStr += "With " + cm.get(req.headers("tabid")).messagesSent + " sent\n";
//            contextStr = cm.get(req.headers("tabid")).messages.stream().map((msg) -> msg + "\n").reduce(contextStr, String::concat);
//            
//            return contextStr;
//        });
    }

    private static spark.Session getSession(spark.Request req) {
        spark.Session s = req.session(true);
        if (s.isNew()) {
            s.attribute("seen", -1);
        }
        return s;
    }

    private static String user(spark.Request req) {
        String user = getSession(req).attribute("user");
        if (user == null) {
            user = "unknown";
        }
        return user;
    }

    private static void setSeenAttribute(spark.Request req, int index) {
        getSession(req).attribute("seen", index);
    }

    private static int getSeenIndex(spark.Request req) {
        return getSession(req).attribute("seen");
    }

    private static void login(spark.Request req, String username) {
        getSession(req).attribute("user", username);
//        HashMap<String, Context> cm = req.session().attribute("map");
//        cm.get(req.headers("tabid")).username = username;
        System.out.println("Login success!");
    }

}
