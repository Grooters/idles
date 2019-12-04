package io.github.grooters.idles.Presenter;


import android.content.Context;
import android.text.TextWatcher;

public interface ILoginP {

    void showPassword(boolean b);

    void rememberAccount(Context context, boolean b, String number, String password);

    TextWatcher getTextWatcher();

    void findPassword();

    void login(Context context, String number, String password);

    void loginAsVisitor(Context context);

    void judgeAccount(Context context);

    boolean getIsRemember();

    void getVerification(String phoneNumber);

    void verify(String code);

}
