package io.github.grooters.idles.net.api;

import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Verification;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("getUser")
    Observable<User> getUser(@Field("number") String number, @Field("password") String password);

    @GET("getTokenByVisitor")
    Observable<Token> getTokenAsVisitor();

    @FormUrlEncoded
    @POST("getVerification")
    Observable<Verification> getVerification(@Field("email") String email);

    @FormUrlEncoded
    @POST("register")
    Observable<User> setUser(@Field("phoneNumber") String phoneNumber, @Field("password") String password);

}
