package io.github.grooters.idles.model;

import io.github.grooters.idles.net.ModelCallBack;

public interface ILoginM {

    public void login(String number, String password, ModelCallBack callBack);

    public void getUser(String token, ModelCallBack callBack);

    public void loginAsVisitor(ModelCallBack callBack);

    public void getCode(String email, ModelCallBack callBack);

    public void register(String email, String password, String code, ModelCallBack callBack);

}
