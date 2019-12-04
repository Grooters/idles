package io.github.grooters.idles.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Create by 李林浪 in 2019/4/22
 * Elegant Code...
 */
public class StorageUtil {

    private static final String TAG = "CacheUtil";

    public static void setInfoToShare(Context context,String name,String key,String[] caches){

        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        for(String cache:caches){

            editor.putString(key,cache);

        }

        editor.apply();

    }

    public static Map<String,String> getInfoFromShare(Context context, String name, String[] keys){

        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        Map<String,String> values = new HashMap<>();

        for(String key : keys){

            values.put(key, Objects.requireNonNull(sharedPreferences.getString(key, null)));

        }

        return values;

    }


    public static StringBuffer getInfoFromFile(File file){

        StringBuffer buffer = new StringBuffer();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while((line = reader.readLine())!=null){

                buffer.append(line);

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return buffer;

    }

    public static void setInfoFromFile(File file,String content){

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(content);

            writer.flush();

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void setInfoFromFile(File file,byte[] content){

        try {

            OutputStream outputStream = new FileOutputStream(file);

            outputStream.write(content);

            outputStream.flush();

            outputStream.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
