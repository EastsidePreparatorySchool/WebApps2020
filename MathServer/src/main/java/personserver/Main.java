/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.mathserver;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        test();
        if (true) {
            return;
        }

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
            System.out.println("Addition requested");
            String p1 = req.queryParams("p1");
            String p2 = req.queryParams("p2");

            if (p1 == null || p2 == null) {
                throw halt();
            }

            double d1 = Double.parseDouble(p1);
            double d2 = Double.parseDouble(p2);

            return d1 + d2;
        });

    }

    spark.Session getSession(spark.Request req) {
        spark.Session s = req.session(true); // if there is none, make one
        return s;
    }

    String user(spark.Request req) {
        String user = getSession(req).attribute("user");
        if (user == null) {
            user = "unknown"; // default name
        }
        return user;
    }
    
    void login(spark.Request req) {
        // take parameter from URL
        String param = ""; // you need to replace this code
        
        getSession(req).attribute("user", param);
    }
}
