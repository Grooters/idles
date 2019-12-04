package io.github.grooters.idles.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by 李林浪 in 2019/4/22
 * Elegant Code...
 */
public class Retrofiter {

    public static <T>T getApi(Context context, Class<T> cls, String url, long maxSize){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(new Cache(new File(context.getFilesDir().getPath()),maxSize)).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(cls);
    }

    public static <T>T getApi(Class<T> cls, String url){

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(cls);
    }

}
