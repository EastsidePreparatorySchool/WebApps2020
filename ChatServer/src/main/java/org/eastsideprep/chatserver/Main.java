/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

import java.util.ArrayList;
import java.util.List;
import static spark.Spark.*;

class Message {

    public String userName;
    public String messageString;

    public Message(String fromUser, String msgString) {
        userName = fromUser;
        messageString = msgString;
    }
}

// @Author: Kenneth Y.
public class Main {

    public static final ArrayList<Message> allMessagesArrayList
            = new ArrayList<>();

    public static void main(String[] args) {
        // tell spark where to find all the HTML and JS
        staticFiles.location("/");
        port(80);

//        // Manually Populate
//        allMessagesArrayList.add("Message 1");
//        allMessagesArrayList.add("Second Message");
//        allMessagesArrayList.add("Something else");

        // get all new messages
        get("/update_messages", (req, res) -> {
            System.out.println("Update messages requested: ");

            String msgs = "";

            synchronized (allMessagesArrayList) {
                int lastSeenIndex = getSeenIndex(req) + 1;
                final List<Message> newMsgs
                        = allMessagesArrayList.subList(
                                lastSeenIndex, allMessagesArrayList.size());

                if (newMsgs.size() > 0) {
//                    msgs = String.join("\n", newMsgs);
//                    msgs += "\n";
                    msgs = new JSONRT().render(newMsgs);
                    setSeenAttribute(req, allMessagesArrayList.size() - 1);
                }
            }

            return msgs;
        });

        // Send message
        put("/send_message", (req, res) -> {
            System.out.println("Send message requested");

            String msg = req.queryParams("msg");

            synchronized (allMessagesArrayList) {
//                allMessagesArrayList.add("From User " + user(req) + " on "
//                        + req.session().id() + ":");
//                allMessagesArrayList.add(msg);
                allMessagesArrayList.add(new Message(user(req), msg));
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
    }

}
