/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.mathserver;

import java.util.ArrayList;
import static spark.Spark.*;

public class Main {

    public static ArrayList<String> messages = new ArrayList<String>();

    public static void main(String[] args) {
        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("/");

        get("/getnew", (req, res) -> {
            System.out.println("New Messages Requested by " + getUser(req) + " " + req.ip());
            String result = "";
            synchronized (messages) {
                for (String s : messages) {
                    result += s;
                    result += "\n";
                }
            }
            return result;
        });

        get("/addmsg", (req, res) -> {
            System.out.println("Send Message Requested");
            String message = req.queryParams("message");
            synchronized (messages) {
                messages.add(getUser(req) + ": " + message);
            }
            return "yay";
        });

        get("/login", (req, res) -> {
            System.out.println("Login Requested");
            String inputName = req.queryParams("usr");
            System.out.println("Logging in as " + inputName + "...");
            getSession(req).attribute("usr", inputName);
            System.out.println("Username set to " + getUser(req));
            return "logged in as " + getUser(req);

        });

        get("/getusr", (req, res) -> {
            System.out.println("Get user requested, ip " + req.ip());
            return getUser(req);
        });

        get("/clear", (req, res) -> {
            System.out.println("Clearing messages");
            synchronized (messages) {
                messages = new ArrayList<String>();
            }
            return "Cleared messages";
        });

        get("/logout", (req, res) -> {
            System.out.println("Logout requested by user " + getUser(req));
            getSession(req).attribute("usr", null);
            return "logged out";
        });

        get("/admin", (req, res) -> {
            System.out.println("Registering user as admin at " + req.ip());
            getSession(req).attribute("usr", "admin");
            return req.ip();
        });

    }

    static spark.Session getSession(spark.Request req) {
        return req.session(true);
    }

    static String getUser(spark.Request req) {
        String user = getSession(req).attribute("usr");
        if (user == null) {
            user = "unknown";
        }
        return user;
    }
}
