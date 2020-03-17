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

    public static ArrayList<String> allMessagesArrayList = new ArrayList<>();

    public static void main(String[] args) {
        // tell spark where to find all the HTML and JS
        staticFiles.location("/");
        port(80);

        // get a silly route up for testing
        get("/hello", (req, res) -> {
            System.out.println("Hey we were invoked:");
            return "Hello world from code";
        });

        
        // Manually Populate
        allMessagesArrayList.add("Message 1");
        allMessagesArrayList.add("Second Message");
        allMessagesArrayList.add("Something else");
        
        
        // get all new messages
        get("/update_messages", (req, res) -> {
            System.out.println("Update messages requested: ");

            String msgs = "";

            allMessagesArrayList.add(req.session().id());
//            allMessagesArrayList.add(req.session().toString());

            for (int i = 0;
                    i < allMessagesArrayList.size();
                    i++) {
                String get = allMessagesArrayList.get(i);

                msgs += get;
                msgs += "\n";
            }

            return msgs;
        });
        
        // Send message
        put("/send_message", (req, res) -> {
            System.out.println("Send message requested");
            
            
            String msg = req.queryParams("msg");

            allMessagesArrayList.add(msg);
            allMessagesArrayList.add(req.session().id());
            
            return msg;
        });

        // Subtract stuff
        get("/minus", (req, res) -> {
            System.out.println("Subtraction requested");

            // Get's inputs as Strings from req
            String p1 = req.queryParams("p1");
            String p2 = req.queryParams("p2");

            // Check if inputs are null
            if (p1 == null || p2 == null) {
                throw halt();
            }

            // Converts inputs to Double
            double d1 = Double.parseDouble(p1);
            double d2 = Double.parseDouble(p2);

            // Return addition of doubles
            return d1 - d2;
        });

    }

}
