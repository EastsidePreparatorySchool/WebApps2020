package org.eastsideprep.chatserver;

import com.google.gson.*;
import spark.*;

public class JSONRT implements ResponseTransformer {

    final static public Gson gson = new Gson();

    @Override
    public String render(Object o) {
        return gson.toJson(o);
    }
}

