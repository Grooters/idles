package io.github.grooters.idles.view.fragment.inter;

import io.github.grooters.idles.base.IBaseView;

public interface IAccountFragment extends IBaseView {

    void setPasswordEditVisible();

    void showVerificationError(String message);

    void showSuccess(String message);

    void showFailure(String message);

    void initVerificationTextView();

}
