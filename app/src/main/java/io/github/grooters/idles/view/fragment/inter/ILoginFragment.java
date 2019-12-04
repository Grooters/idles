package io.github.grooters.idles.view.fragment.inter;

import io.github.grooters.idles.base.IBaseView;

public interface ILoginFragment extends IBaseView {

    public void showPassword();

    public void hidePassword();

    public void rememberAccount();

    public void forgetAccount();

    public void showClearButton();

    public void hideClearButton();

    public void loginFailure(String message);

    public void loginSuccess();

    public void loginFailureAsVisitor();

    public void loginSuccessAsVisitor();

    public void initAccount(String number, String password);

}
