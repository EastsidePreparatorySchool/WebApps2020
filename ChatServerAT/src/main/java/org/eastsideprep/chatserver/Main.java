/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import static spark.Spark.*;

class Message { // created class to be able to add more details to message

    public String username;
    public String msg;
    public String msgtime;
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
            Message newMessage = new Message(); // creating message object
            newMessage.username = user(req); // setting value of username property 

            // wanted to add timestamp, learned how to from: https://www.javatpoint.com/java-get-current-date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            newMessage.msgtime = dtf.format(LocalDateTime.now()); // setting value of msgtime property
            newMessage.msg = msg; // setting value of msg property

            synchronized (msgs) {
                msgs.add(newMessage); // adding message object to arraylist
                //msgs.add(user(req)+"~ "+msg); //combine username
                System.out.println(msgs.toString());
            }

            return HttpStatus.ACCEPTED_202; // returning that our request was accepted
        });

        get("/get", "application/json", (req, res) -> {
            JSONRT rt = new JSONRT(); // created a response transformer object
            synchronized (msgs) {
                String result = rt.render(msgs); // rendered java objects into JSON string

                return result;
            }
        }, new JSONRT());

        get("/login", (req, res) -> {             
            System.out.println(req.host());  
            String ifLoggedIn = "http://" + req.host() + "/login_user";
            String red = "https://epsauth.azurewebsites.net/login?url=" + 
                    ifLoggedIn + "&loginparam=useremail";
            System.out.println("EPSAuth: redirecting: " + red);
            res.redirect(red, 302);
            
            String userEmail = req.queryParams("useremail");
            System.out.println(userEmail);

            return userEmail;
        });

        get("/login_user", (req, res) -> {
            System.out.println("I MADE IT");
            System.out.println(req.queryParams("useremail"));
            String user = req.queryParams("useremail");

            getSession(req).attribute("username", user);

            System.out.print(user);
            
            res.redirect("/");
            return user;
        });
        
        get("/headers", (req, res) -> {
            String result = "";

            for (String s : req.headers()) {
                result += s + ":" + req.headers(s) + "<br>";
            }

            return result;
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
