/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

/**
 *
 * @author kyang
 */

import com.google.gson.*;
import spark.ResponseTransformer;

public class JSONRT implements ResponseTransformer {
    final static private Gson gson = new Gson();
    
    @Override
    public String render(Object o) {
        return gson.toJson(o);
    }
}
