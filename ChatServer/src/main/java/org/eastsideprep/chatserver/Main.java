/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

import java.util.ArrayList;
import static spark.Spark.*;

// @Author: Kenneth Y.
public class Main {

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
        get("/update_messages", (req, res) -> {
            System.out.println("Update messages requested: ");

            String msgs = "";

            synchronized (allMessagesArrayList) {
                int lastSeenIndex = getSeenIndex(req);

                allMessagesArrayList.add(req.session().id());
//            allMessagesArrayList.add(req.session().toString());

                for (int i = 0;
                        i < allMessagesArrayList.size();
                        i++) {
                    if (i < lastSeenIndex) {
                        continue;
                    }
                    String get = allMessagesArrayList.get(i);

                    msgs += get;
                    msgs += "\n";
                }
            }

            return msgs;
        });

        // Send message
        put("/send_message", (req, res) -> {
            System.out.println("Send message requested");

            String msg = req.queryParams("msg");

            synchronized (allMessagesArrayList) {
                allMessagesArrayList.add("From User " + user(req) + ":");
                allMessagesArrayList.add(msg);
                allMessagesArrayList.add(req.session().id());
                setSeenAttribute(req, allMessagesArrayList.size() - 1);
            }

            return msg;
        });

        // Login route
        put("/login_user", (req, res) -> {
            System.out.println("Login user requested");

            String username = req.queryParams("username");

            login(req, username);

            return username;
        });
    }

    private static spark.Session getSession(spark.Request req) {
        spark.Session s = req.session(true);
        if (s.isNew()) {
            s.attribute("seen", 0);
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
    }

}
