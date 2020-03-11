/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.mathserver;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        // get a silly route up for testing
        get("/hello", (req, res) -> "Hello World");
        
        // tell spark where to find all the HTML and JS
        staticFiles.location("/public");
    }

}
