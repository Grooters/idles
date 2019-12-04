package io.github.grooters.idles.Presenter.imple;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import com.orhanobut.logger.Logger;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.model.ILoginM;
import io.github.grooters.idles.model.imple.LoginM;
import io.github.grooters.idles.net.ModelCallBack;
import io.github.grooters.idles.net.ResponseCode;
import io.github.grooters.idles.utils.Encrypter;
import io.github.grooters.idles.utils.FileIOer;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.view.fragment.LoginFragment;
import io.github.grooters.idles.view.fragment.inter.ILoginFragment;

public class LoginP implements ILoginP {

    private ILoginFragment iLoginFragment;

    private ILoginM iLoginM;

    private final String ACCOUNT_INFO = Encrypter.md5("accountInfo");

    private boolean isRememberAccount;

    private User user;

    public LoginP(LoginFragment loginFragment){

        iLoginFragment = loginFragment;

        iLoginM = new LoginM(iLoginFragment, this);

    }

    @Override
    public void login(final Context context, String number, String password) {

        iLoginFragment.startLading();

        iLoginM.login(number, password, new ModelCallBack() {
            @Override
            public void success(Object data) {

                user = (User)data;

                String token = user.getToken();

                switch (token){
                    case ResponseCode.PASS_ERROR:
                        iLoginFragment.loginFailure("账号或密码错误");
                        return;
                    case ResponseCode.ACCOUNT_NON:
                        iLoginFragment.loginFailure("账户不存在");
                        return;
                    case ResponseCode.UNKNOWN:
                        iLoginFragment.loginFailure("未知错误，请稍后再试");
                        return;
                }

                if( isRememberAccount)

                    FileIOer.writeString(context, ACCOUNT_INFO, Encrypter.toBase64(Jsoner.toJson(user)));

                iLoginFragment.stopLading();

                iLoginFragment.loginSuccess();

            }

            @Override
            public void failure(String message) {

                iLoginFragment.stopLading();

                iLoginFragment.loginFailure(message);

            }

        });


    }

    @Override
    public void loginAsVisitor(Context context) {

        // 拿到临时访问令牌
        iLoginM.loginAsVisitor(new ModelCallBack() {
            @Override
            public void success(Object data) {

                User user = (User)data;

                if( user.getToken() != null ){

                    iLoginFragment.loginSuccessAsVisitor();

                }else{

                    iLoginFragment.loginFailureAsVisitor();

                }

            }

            @Override
            public void failure(String message) {

                iLoginFragment.loginFailureAsVisitor();

            }
        });


    }

    // 应用运行时获取账户信息，若存在账户信息，则填充视图账号和密码编辑框
    @Override
    public void getAccountInfo(Context context) {

        String json = FileIOer.readString(context, ACCOUNT_INFO);

        Logger.d(json);

        if(json != null){

            User user = Jsoner.toObj(Encrypter.fromBase64(json), User.class);

            iLoginFragment.initAccount(user.getNumber(), user.getPassword());

        }

    }

    // 判断出更改何种图标，是否让密码显示
    @Override
    public void showPassword(boolean b) {

        if(b){

            iLoginFragment.showPassword();

        }else{

            iLoginFragment.hidePassword();

        }

    }

    // 判断出更改何种图标，记录是否记住账号
    @Override
    public void rememberAccount(Context context, boolean b, String number, String password) {

        if(b){

            Logger.d(ACCOUNT_INFO);


            iLoginFragment.rememberAccount();

        }else{


            iLoginFragment.forgetAccount();

        }

        isRememberAccount = b;
    }

    // 提供编辑框内容监听器对象给视图
    @Override
    public TextWatcher getTextWatcher() {

        return new IsEmpty();

    }

    @Override
    public void findPassword() {

    }

    @Override
    public void registerAccount() {

    }

    class IsEmpty implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if( s.toString().equals("") ){

                iLoginFragment.hideClearButton();

            }else {

                iLoginFragment.showClearButton();

            }

        }

        @Override
        public void afterTextChanged(Editable s) { }
    }

}
