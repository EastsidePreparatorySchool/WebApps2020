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

        messages.add("hi");
        messages.add("hello world");
        messages.add("typing");
        messages.add("MINECRAFT");
        messages.add("caskdja;lskdf;alks;df");
        messages.add(";ojifekjfes;");
        messages.add("windows 10 is gud");
        messages.add("Macos is gud");
        
        
        // get a silly route up for testing
        get("/hello", (req, res) -> {
            System.out.println("Hey we were invoked:");
            return "Hello world from code";
        });
        
        get("/getnew", (req, res) -> {
            System.out.println("New Messages Requested");
            String result = "";
            for(String s : messages){
                result += s;
                result += "\n";
            }
            result += req.session().toString();
            return result;
        });
        
        get("/addmsg", (req, res) -> {
            System.out.println("Send Message Requested");
            String message = req.queryParams("message");
            messages.add(message);
            return "yay";
        });
    }

}
