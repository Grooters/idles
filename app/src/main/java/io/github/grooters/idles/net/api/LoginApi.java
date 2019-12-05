package io.github.grooters.idles.net.api;

import io.github.grooters.idles.bean.User;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("getToken")
    Observable<ResponseBody> getToken(@Field("number") String number, @Field("password") String password);

    @FormUrlEncoded
    @POST("getUser")
    Observable<User> getUser(@Field("token") String token);

    @GET("getTokenByVisitor")
    Observable<ResponseBody> getTokenByVisitor();

    @FormUrlEncoded
    @POST("getVerification")
    Observable<ResponseBody> getVerification(@Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST("register")
    Observable<User> register(@Field("phoneNumber") String phoneNumber, @Field("password") String password);

}
