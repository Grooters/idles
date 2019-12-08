package io.github.grooters.idles.view.fragment;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.orhanobut.logger.Logger;
import java.util.Objects;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.Presenter.imple.LoginP;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.BaseFragment;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.LoginActivity;
import io.github.grooters.idles.view.activity.MainActivity;
import io.github.grooters.idles.view.fragment.inter.ILoginFragment;
import io.github.grooters.idles.view.widget.LoadingWidget;

/**
 * Create by 李林浪 in 2019/6/27
 * Elegant Code...
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener, ILoginFragment {


    private ILoginP iLoginP;

    private EditText accountEdit, passWordEdit;

    private ImageView rememberImage, showHideImage, clearImage;

    private boolean isShowPassword;

    private LoadingWidget loadingText;

    @Override
    public void initView(View view) {

        iLoginP = new LoginP(this);

        assert view != null;
        ConstraintLayout constraintLayout = view.findViewById(R.id.login_constrain_layout);

        accountEdit = view.findViewById(R.id.edit_account);

        passWordEdit = view.findViewById(R.id.edit_password);

        rememberImage = view.findViewById(R.id.img_remember);

        showHideImage = view.findViewById(R.id.img_show_hide);

        TextView forgetText = view.findViewById(R.id.text_forget);

        TextView registerText = view.findViewById(R.id.text_register);

        TextView visitorText = view.findViewById(R.id.text_visitor);

        clearImage = view.findViewById(R.id.img_clear);

        Button loginButton = view.findViewById(R.id.butt_login);

        loadingText = view.findViewById(R.id.text_loading);

        constraintLayout.setOnClickListener(this);

        rememberImage.setOnClickListener(this);

        showHideImage.setOnClickListener(this);

        forgetText.setOnClickListener(this);

        registerText.setOnClickListener(this);

        clearImage.setOnClickListener(this);

        accountEdit.addTextChangedListener(iLoginP.getTextWatcher());

        loginButton.setOnClickListener(this);

        visitorText.setOnClickListener(this);

        iLoginP.judgeAccount(Objects.requireNonNull(getActivity()).getApplicationContext());
    }

    @Override
    public int getLayout() {

        return R.layout.fragment_login;

    }

    @Override
    public void startLading() {

        loadingText.start();

    }

    @Override
    public void stopLading() {

        loadingText.stop();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.login_constrain_layout:

                accountEdit.clearFocus();

                passWordEdit.clearFocus();

                break;

            case R.id.img_remember:

                iLoginP.rememberAccount(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        iLoginP.getIsRemember(), accountEdit.getText().toString(), passWordEdit.getText().toString());

                break;

            case R.id.img_show_hide:

                isShowPassword = !isShowPassword;

                iLoginP.showPassword(isShowPassword);

                break;

            case R.id.text_forget:

                Logger.d("text_forget");

                iLoginP.findPassword();

                break;

            case R.id.text_register:

                ((LoginActivity) Objects.requireNonNull(getActivity()))
                        .replaceFragment( new AccountFragment(), "AccountFragment");

                break;

            case R.id.img_clear:

                accountEdit.setText(null);

                break;

            case R.id.butt_login:

                iLoginP.login(Objects.requireNonNull(getActivity()).getApplication(),
                        accountEdit.getText().toString(), passWordEdit.getText().toString());

                break;

            case R.id.text_visitor:

                iLoginP.loginAsVisitor(Objects.requireNonNull(getActivity()).getApplicationContext());

                break;

        }
    }

    @Override
    public void loginFailure(String message) {

        Toaster.shortShow(getActivity(), message);

    }

    @Override
    public void loginSuccess() {

        Toaster.shortShow(getActivity(), "登录成功");

        Intenter.jumpActivity(getActivity(), MainActivity.class);

    }

    @Override
    public void loginFailureAsVisitor() {

        Toaster.shortShow(getActivity(), "登录失败");

    }

    @Override
    public void loginSuccessAsVisitor() {

        Toaster.longShow(getActivity(), "以游客身份登录");

        Intenter.jumpActivity(getActivity(), MainActivity.class);

    }

    @Override
    public void initAccount(String number, String password) {

        accountEdit.setText(number);

        passWordEdit.setText(password);

    }

    @Override
    public void showPassword() {

        showHideImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_password_press, null));

        passWordEdit.setInputType(InputType.TYPE_CLASS_TEXT);

    }

    @Override
    public void hidePassword() {

        showHideImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_password, null));

        passWordEdit.setInputType(129);

    }

    @Override
    public void rememberAccount() {

        rememberImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_account_press, null));

    }

    @Override
    public void forgetAccount() {

        rememberImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_account, null));

    }

    @Override
    public void showClearButton() {

        clearImage.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideClearButton() {

        clearImage.setVisibility(View.GONE);

    }


}
