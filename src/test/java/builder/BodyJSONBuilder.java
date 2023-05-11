package builder;

import com.google.gson.Gson;

public class BodyJSONBuilder {

    public static String getJSONContent(Object dataObject){
        return new Gson().toJson(dataObject);
    }
}
