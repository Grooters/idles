package io.github.grooters.idles.net.api;

import io.github.grooters.idles.bean.User;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginApi {

    @GET("login")
    Observable<ResponseBody> login(@Query("number") String number, @Query("password")String password);

    @GET("getUser")
    Observable<User> getUser(@Query("token") String token);

    @GET("loginAsVisitor")
    Observable<ResponseBody> getVisitor();

    @GET("register")
    Observable<ResponseBody> getCode(@Query("email") String email);

    @GET("register")
    Observable<User> register(@Query("email") String email, @Query("password")String password, @Query("code") String code);

}
