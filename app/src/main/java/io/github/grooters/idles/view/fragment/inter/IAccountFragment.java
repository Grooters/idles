package io.github.grooters.idles.view.fragment.inter;

import io.github.grooters.idles.base.IBaseView;

public interface IAccountFragment extends IBaseView {

    void setPasswordEditVisible();

    void setPasswordVisibleGone();

//    void showVerifyError(String message);

    void showVerificationSuccess(String message);
//
//    void showVerificationFailure(String message);

    void setRegisterButtonText(String text);

    void showFailure(String message);

    void initVerificationTextView();

    void showRegisterSuccess(String message);

}
