package io.github.grooters.idles.view.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Objects;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.Presenter.imple.LoginP;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.BaseFragment;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.LoginActivity;
import io.github.grooters.idles.view.fragment.inter.IAccountFragment;

public class AccountFragment extends BaseFragment implements View.OnClickListener, IAccountFragment {

    private EditText passwordEdit, passwordSecondEdit, phoneEdit, verificationEdit;

    private TextView verificationTextView;

    private Button registerButton;

    private ILoginP iLoginP;

    private int type;

    AccountFragment(int type){

        this.type = type;

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_account;
    }

    @Override
    public void initView(View view) {

        phoneEdit = view.findViewById(R.id.edit_email);

        verificationEdit = view.findViewById(R.id.edit_verification);

        passwordEdit = view.findViewById(R.id.edit_password);

        passwordSecondEdit = view.findViewById(R.id.edit_password_second);

        verificationTextView = view.findViewById(R.id.text_verification);

        TextView verifyTextView = view.findViewById(R.id.text_verify);

        TextView backLoginTextView = view.findViewById(R.id.text_back_login);

        registerButton = view.findViewById(R.id.butt_register);

        phoneEdit.setOnClickListener(this);

        verificationEdit.setOnClickListener(this);

        passwordEdit.setOnClickListener(this);

        passwordSecondEdit.setOnClickListener(this);

        verifyTextView.setOnClickListener(this);

        verificationTextView.setOnClickListener(this);

        backLoginTextView.setOnClickListener(this);

        registerButton.setOnClickListener(this);

        iLoginP = new LoginP(this);

    }

    @Override
    public void setRegisterButtonText(String text) {

        registerButton.setText(text);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.text_verify:

                iLoginP.verify(verificationEdit.getText().toString());

                break;

            case R.id.text_verification:

                iLoginP.getVerification(getActivity(), phoneEdit.getText().toString());

                break;

            case R.id.butt_register:

                iLoginP.register(getActivity(), type, phoneEdit.getText().toString(),
                        verificationTextView.getText().toString(), passwordEdit.getText().toString(), passwordSecondEdit.getText().toString());

                break;

            case R.id.text_back_login:

                ((LoginActivity) Objects.requireNonNull(getActivity()))
                        .replaceFragment( new LoginFragment(), "LoginFragment");

                break;

        }

    }

    @Override
    public void showVerificationSuccess(String message) {

        verificationTextView.setTextColor(getResources().getColor(R.color.text_verification_press,null));

        Toaster.shortShow(getActivity(),message);

    }

//    @Override
//    public void showVerificationFailure(String message) {
//
//        Toaster.shortShow(getActivity(),message);
//
//    }

    @Override
    public void initVerificationTextView() {

        verificationTextView.setTextColor(getResources().getColor(R.color.text_verification,null));

    }

    @Override
    public void setPasswordEditVisible() {

        Toaster.shortShow(getActivity(),"验证成功");

        passwordEdit.setVisibility(View.VISIBLE);

        passwordSecondEdit.setVisibility(View.VISIBLE);

        registerButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void setPasswordVisibleGone() {

        passwordEdit.setVisibility(View.GONE);

        passwordSecondEdit.setVisibility(View.VISIBLE);

    }

    //    @Override
//    public void showVerifyError(String message) {
//
//        Toaster.shortShow(getActivity(),message);
//
//    }


    @Override
    public void showFailure(String message) {

        Toaster.shortShow(getActivity(), message);

    }

    @Override
    public void showRegisterSuccess(String message) {

        Toaster.shortShow(getActivity(), message);

        ((LoginActivity) Objects.requireNonNull(getActivity()))
                .replaceFragment( new LoginFragment(), "LoginFragment");

    }

    @Override
    public void startLading() {

    }

    @Override
    public void stopLading() {

    }

}
