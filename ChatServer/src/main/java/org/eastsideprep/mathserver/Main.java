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

    public static ArrayList<String> loggedInUsers = new ArrayList<String>();
    
    public static String[] illegalUsernames = {
        "ADMIN",
        "HACK",
        "SCAM",
        "SERVER"
    };

    public static void main(String[] args) {
        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("/");

        get("/getnew", (req, res) -> {
            //System.out.println("New Messages Requested by " + getUser(req) + " " + req.ip());
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

            String inputName = req.queryParams("usr");

            for (String u : illegalUsernames) {
                if (inputName.toUpperCase().contains(u)) {
                    String newUsr = "usr_" + req.ip();
                    getSession(req).attribute("usr", newUsr);
                    
                    System.out.println("Username " + inputName + " was illegal, renaming to " + newUsr);
                    
                    loggedInUsers.add(newUsr);
                    
                    return newUsr;
                }
                
            }
            
            for (String u : loggedInUsers) {
                if (inputName.toLowerCase().equals(u.toLowerCase())){
                    String newUsr = inputName + "_2";
                    getSession(req).attribute("usr", newUsr);
                    
                    System.out.println("Username " + inputName + " already existed, renaming to " + newUsr);
                    
                    loggedInUsers.add(newUsr);
                    
                    return newUsr;
                }
            }

            System.out.println("Logging ip " + req.ip() + " as " + inputName + "...");

            getSession(req).attribute("usr", inputName);

            System.out.println("Username set to " + getUser(req));

            loggedInUsers.add(inputName);
            
            return inputName;

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
            
            loggedInUsers.remove(getUser(req));
            
            getSession(req).attribute("usr", null);
            
            return "Logged out. <a href='/'>log in</a>";
        });

        get("/admin", (req, res) -> {
            System.out.println("Registering user as admin at " + req.ip());
            getSession(req).attribute("usr", "admin");
            return req.ip() + " logged in as admin. <a href='/'>chat</a>";
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
