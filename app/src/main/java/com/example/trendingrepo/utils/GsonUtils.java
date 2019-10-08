package com.example.trendingrepo.utils;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class GsonUtils {
    public static String getJsonFromObject(Object object, Type type) {
        Gson gson = new Gson();
        return gson.toJson(object, type);
    }

    public static Object getObjectFromJson(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

}
