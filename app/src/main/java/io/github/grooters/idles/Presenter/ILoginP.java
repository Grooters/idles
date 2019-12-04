package io.github.grooters.idles.Presenter;


import android.content.Context;
import android.text.TextWatcher;

public interface ILoginP {

    public void showPassword(boolean b);

    public void rememberAccount(Context context, boolean b, String number, String password);

    public TextWatcher getTextWatcher();

    public void findPassword();

    public void registerAccount();

    public void login(Context context, String number, String password);

    public void loginAsVisitor(Context context);

    public void getAccountInfo(Context context);

}
