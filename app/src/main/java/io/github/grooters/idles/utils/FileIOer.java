package io.github.grooters.idles.utils;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import	java.io.FileWriter;

import java.io.File;
import java.io.IOException;

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

        String message = null;

        try {

            FileReader reader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(reader);

            message = bufferedReader.readLine();

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;

    }

}
