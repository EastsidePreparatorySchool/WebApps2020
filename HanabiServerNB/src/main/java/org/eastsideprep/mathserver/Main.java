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

//HANABI SERVER NB

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

    }
}
