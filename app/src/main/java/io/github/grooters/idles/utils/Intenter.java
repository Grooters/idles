package io.github.grooters.idles.utils;

import android.content.Context;
import android.content.Intent;

public class Intenter {

    public static void jumpActivity(Context context, Class<?> cls){

        Intent intent = new Intent(context, cls);

        context.startActivity(intent);

    }

}
