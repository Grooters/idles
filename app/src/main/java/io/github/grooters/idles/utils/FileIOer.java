package io.github.grooters.idles.utils;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import	java.io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileIOer {

    public static void writeString(Context context, String fileName, String message){

        Logger.d(context.getFilesDir().getPath() + "/" + fileName);

        File file = new File(context.getFilesDir().getPath() + "/" + fileName);

        try {

            FileWriter writer = new FileWriter(file);

            writer.write(message);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readString(Context context, String fileName){

        File file = new File(context.getFilesDir().getPath() + "/" +fileName);

        StringBuilder message = new StringBuilder();

        try {

            FileReader reader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(reader);

            String temp;

            while ((temp = bufferedReader.readLine()) != null){

                message.append(temp);

            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return message.toString();

    }

    public static boolean delete(Context context, String fileName){

        File file = new File(context.getFilesDir().getPath() + "/" +fileName);

        if(file.exists())

            return file.delete();

        else

            return false;

    }

}
