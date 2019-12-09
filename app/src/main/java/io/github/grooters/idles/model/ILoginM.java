package io.github.grooters.idles.model;

import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Verification;
import io.github.grooters.idles.bean.data.GetUserData;
import io.github.grooters.idles.net.ModelCallBack;

public interface ILoginM {

    void getUserNoToken(String number, String password, ModelCallBack<GetUserData> callBack);

    void getUserWithToken(String token, ModelCallBack<GetUserData> callBack);

    void getToken(ModelCallBack<Token> callBack);

    void getVerification(String phoneNumber, ModelCallBack<Verification> callBack);

    void setUser(String phoneNumber, String password, ModelCallBack<User> callBack);

    public void getUserWithEmail(String email, String verification, String password, final ModelCallBack<GetUserData> callBack);

}
