package io.github.grooters.idles.model;

import io.github.grooters.idles.net.ModelCallBack;

public interface ILoginM {

    void getUserNoToken(String number, String password, ModelCallBack callBack);

    void getUser(String token, ModelCallBack callBack);

    void getToken(ModelCallBack callBack);

    void getVerification(String phoneNumber, ModelCallBack callBack);

    void setUser(String phoneNumber, String password, ModelCallBack callBack);

}
