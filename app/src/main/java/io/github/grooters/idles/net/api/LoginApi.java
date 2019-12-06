package io.github.grooters.idles.net.api;

import io.github.grooters.idles.base.BaseBean;
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
    @POST("getToken")
    Observable<BaseBean<Token>> getToken(@Field("number") String number, @Field("password") String password);

    @FormUrlEncoded
    @POST("getUser")
    Observable<BaseBean<User>> getUser(@Field("token") String token);

    @GET("getTokenByVisitor")
    Observable<BaseBean<Token>> getTokenByVisitor();

    @FormUrlEncoded
    @POST("getVerification")
    Observable<BaseBean<Verification>> getVerification(@Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST("register")
    Observable<BaseBean<User>> register(@Field("phoneNumber") String phoneNumber, @Field("password") String password);

}
