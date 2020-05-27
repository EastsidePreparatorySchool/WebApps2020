package personserver;

import static spark.Spark.*;

public class Main {
    // an example person
    static Person aPerson = new Person("Gunnar", "Mein", 100, "None");

    public static void main(String[] args) {

        port(80);

        // tell spark where to find all the HTML and JS
        staticFiles.location("/");
        
        // this route returns a string.
        // I call toString() explicitly. Often, when Java knows a String is needed, it is also called implicitly.
        get("person_as_string", (req, res)->{return aPerson.toString();});
        
        // this route return a JSON Person object. 
        // Note the added format info (application/json) and the response transformer object as the last parameter.
        get("person_as_json", "application/json",  (req, res)->{return aPerson;}, new JSONRT());

    }
}
