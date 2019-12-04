package io.github.grooters.idles.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Create by 李林浪 in 2019/4/24
 * Elegant Code...
 */
public class InterUtil {

    public static boolean isNetWorking(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager == null){

            return false;

        }

        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info == null || !info.isAvailable()){

            return false;

        }

        return true;

    }

    public static boolean isWifi(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager == null){

            return false;

        }

        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI ;

    }

}
