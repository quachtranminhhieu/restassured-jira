package model;

import com.google.gson.Gson;

public class JSONHelper {

    public static String getJSONString(PostBody postBody){
        if (postBody == null){
            throw new IllegalArgumentException("PostBody can not be null");
        }
        return new Gson().toJson(postBody);
    }
}
