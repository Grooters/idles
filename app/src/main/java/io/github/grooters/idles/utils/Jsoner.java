package io.github.grooters.idles.utils;

import com.google.gson.Gson;

public class Jsoner {


    public static String toJson(Object obj){

        Gson gson = new Gson();

        return  gson.toJson(obj);

    }

    public static <T>T toObj(String message, Class<T> cls){

        Gson gson = new Gson();

        return gson.fromJson(message, cls);

    }

}
