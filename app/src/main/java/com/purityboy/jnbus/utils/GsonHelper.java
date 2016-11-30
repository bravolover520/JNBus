package com.purityboy.jnbus.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2016/11/28.
 */

public class GsonHelper {

    public static String createGsonString(Object object) {
        if (null != object) {
            return new Gson().toJson(object);
        }
        return null;
    }

    public static <T> T gsonStringToBean(String json, Class<T> cls) {
        if (json.equals("{}"))
            return null;
        return new Gson().fromJson(json, cls);
    }

    public static <T> List<T> gsonStringToList(String json, Class<T> cls) {
        if (!json.startsWith("["))
            return null;
        Gson gson = new Gson();
        List<T> list = null;
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        try {
            list = new ArrayList<T>();
            for (JsonElement element : array) {
                list.add(gson.fromJson(element, cls));
            }
        } catch (Throwable t) {

        }
        return list;
    }
}
