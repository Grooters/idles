package io.github.grooters.idles.utils;


import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import io.github.grooters.idles.bean.User;

public class Jsoner {

    public static void main(String...args){

        User user = new User("12","12","12","12","12","12","12","12","ded","ede",1);

        Gson gson = new Gson();

        System.out.println(gson.toJson(user));

    }

    public static String toJson(Object obj){

        Gson gson = new Gson();

        return  gson.toJson(obj);

    }

    public static <T>T toObj(String message, Class<T> cls){

        Gson gson = new Gson();

        return gson.fromJson(message, cls);

    }

}
