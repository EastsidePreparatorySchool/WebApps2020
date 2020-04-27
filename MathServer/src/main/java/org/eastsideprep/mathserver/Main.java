/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.mathserver;

import static spark.Spark.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("/");

        // get a silly route up for testing
        get("/hello", (req, res) -> {
            System.out.println("Hey we were invoked:");
            return "Hello world from code";
        });

        get("/tau", (req, res) -> {
            System.out.println("Tau requested");
            return 2 * Math.PI;
        });

        get("/plus", (req, res) -> {
            System.out.println("Addition requested :D");
            String p1 = req.queryParams("p1");
            String p2 = req.queryParams("p2");

            if (p1 == null || p2 == null) {
                throw halt();
            }

            double d1 = Double.parseDouble(p1);
            double d2 = Double.parseDouble(p2);

            return d1 + d2;
        });

        get("/minus", (req, res) -> {
            System.out.println("Substraction requested");
            String p1 = req.queryParams("p1");
            String p2 = req.queryParams("p2");

            if (p1 == null || p2 == null) {
                throw halt();
            }

            double d1 = Double.parseDouble(p1);
            double d2 = Double.parseDouble(p2);

            return d1 - d2;
        });

        ArrayList<String> messages = new ArrayList<String>();
        messages.add("First Message");

        get("/update_messages", (req, res) ->{
            synchronized (messages) {
                System.out.println("Get all messages requested");
                String mes = String.join(" \n", messages);
                String msg = new JSONRT().render(mes);
                return (msg);
            }
        });

        put("/send", (req, res) -> {
            synchronized (messages) {
                if (req.session().attribute("username") == null) {
                    req.session().attribute("username", "unknown");
                   
                }
                System.out.println("Send message requested");
                String mess = req.queryParams("message");

                messages.add(req.session().attribute("username") + ": " + mess);

                return mess;
            }
        });
        put("/login", (req, res) -> {
            login(req);
            System.out.println("login attempt: " + req.body());
            String username = req.body();

            return username;
        });
        before("/protected/*", (req, res) -> {
            System.out.println("username");
            if (req.session().attribute("username") == null) {
                System.out.println("username");
                halt(401, "You must login first.");
            }
        });
    }

    private static String login(spark.Request req) {
       
        req.session().attribute("username", req.body());
        return "login successful";
    }
}
