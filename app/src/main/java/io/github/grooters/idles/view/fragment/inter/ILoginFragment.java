package io.github.grooters.idles.view.fragment.inter;

import io.github.grooters.idles.base.IBaseView;

public interface ILoginFragment extends IBaseView {

    void showPassword();

    void hidePassword();

    void rememberAccount();

    void forgetAccount();

    void showClearButton();

    void hideClearButton();

    void loginSuccess();

    void loginSuccessAsVisitor();

    void initAccount(String number, String password);

    void showConfigDialog();

}
