package io.github.grooters.idles.utils;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public static void shortShow(Context context, String message){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public static void longShow(Context context, String message){

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

}
