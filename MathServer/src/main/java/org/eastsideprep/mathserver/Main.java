/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.mathserver;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        // tell spark where to find all the HTML and JS
        staticFiles.location("/");
        port(80);

        // get a silly route up for testing
        get("/hello", (req, res) -> {
            System.out.println("Hey we were invoked:");
            return "Hello world from code";
        });

        // Return the value of Tau
        get("/tau", (req, res) -> {
            System.out.println("Tau requested");
            return 2 * Math.PI;
        });

        // Adds stuff
        get("/plus", (req, res) -> {
            System.out.println("Addition requested");

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
            return d1 + d2;
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
