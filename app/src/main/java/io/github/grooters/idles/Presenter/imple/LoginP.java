package io.github.grooters.idles.Presenter.imple;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import com.orhanobut.logger.Logger;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.base.BaseBean;
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Verification;
import io.github.grooters.idles.bean.data.GetUserData;
import io.github.grooters.idles.model.ILoginM;
import io.github.grooters.idles.model.imple.LoginM;
import io.github.grooters.idles.net.ModelCallBack;
import io.github.grooters.idles.net.ResponseCode;
import io.github.grooters.idles.utils.Encrypter;
import io.github.grooters.idles.utils.FileIOer;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.view.fragment.AccountFragment;
import io.github.grooters.idles.view.fragment.LoginFragment;
import io.github.grooters.idles.view.fragment.inter.IAccountFragment;
import io.github.grooters.idles.view.fragment.inter.ILoginFragment;

public class LoginP implements ILoginP {

    private ILoginFragment iLoginFragment;

    private IAccountFragment iAccountFragment;

    private Verification verification;

    private ILoginM iLoginM;

    private final String ACCOUNT_INFO = Encrypter.md5("accountInfo");

    private final String ACCOUNT_USER = Encrypter.md5("accountUser");

    private final String ACCOUNT_VISITOR = Encrypter.md5("accountVisitor");

    private boolean isRememberAccount;

    private User user;

    public LoginP(LoginFragment loginFragment){

        iLoginFragment = loginFragment;

        iLoginM = new LoginM(iLoginFragment, this);

    }

    public LoginP(AccountFragment accountFragment){

        iAccountFragment = accountFragment;

        iLoginM = new LoginM(iLoginFragment, this);

    }

    @Override
    public void login(final Context context, String number, String password) {

        iLoginFragment.startLading();

        iLoginM.getUserNoToken(number, password, new ModelCallBack<GetUserData>() {

            @Override
            public void success(BaseBean<GetUserData> data) {

                Logger.d(data.getData().getUser().getName());

                int code = data.getCode();

                switch (code){
                    case ResponseCode.PASS_ERROR:
                        iLoginFragment.loginFailure("账号或密码错误");
                        iLoginFragment.stopLading();
                        return;
                    case ResponseCode.ACCOUNT_NON:
                        iLoginFragment.loginFailure("账户不存在");
                        iLoginFragment.stopLading();
                        return;
                    case ResponseCode.UNKNOWN:
                        iLoginFragment.loginFailure("未知错误，请稍后再试");
                        iLoginFragment.stopLading();
                        return;
                }

                user = data.getData().getUser();

                if( isRememberAccount)

                    FileIOer.writeString(context, ACCOUNT_INFO, Encrypter.toBase64(Jsoner.toJson(user)));

                iLoginFragment.stopLading();

                FileIOer.writeString(context, ACCOUNT_USER, Encrypter.toBase64(Jsoner.toJson(user)));

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
    public void loginAsVisitor(final Context context) {
        // 拿到临时访问令牌
        iLoginM.getToken(new ModelCallBack<Token>() {
            @Override
            public void success(BaseBean<Token> tokens) {

                String token = tokens.getData().getToken();

                if( tokens.getCode() != ResponseCode.UNKNOWN ){

                    iLoginFragment.loginSuccessAsVisitor();

                    FileIOer.writeString(context, ACCOUNT_VISITOR, Encrypter.toBase64(Jsoner.toJson(token)));

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
    public void judgeAccount(Context context) {

        String json = FileIOer.readString(context, ACCOUNT_INFO);

        User user = Jsoner.toObj(Encrypter.fromBase64(json), User.class);

        if (user == null)

            return;

        isRememberAccount = true;

        iLoginFragment.rememberAccount();

        iLoginFragment.initAccount(user.getNumber(), user.getPassword());

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

        if(!b){

            Logger.d(ACCOUNT_INFO);

            iLoginFragment.rememberAccount();

        }else{

            iLoginFragment.forgetAccount();

            FileIOer.delete(context, ACCOUNT_INFO);

        }

        isRememberAccount = !b;
    }

    @Override
    public boolean getIsRemember() {

        return isRememberAccount;

    }

    // 提供编辑框内容监听器对象给视图
    @Override
    public TextWatcher getTextWatcher() {

        return new IsEmpty();

    }

    @Override
    public void findPassword() {

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

    // 注册手机账号
    @Override
    public void getVerification(String phoneNumber) {

        iLoginM.getVerification(phoneNumber, new ModelCallBack<Verification>() {
            @Override
            public void success(BaseBean<Verification> verifications) {

                verification = verifications.getData();

            }

            @Override
            public void failure(String message) {

                verification = null;

            }
        });

    }

    @Override
    public void verify(String str) {

        if(verification.getVerification().equals(str)){

            iAccountFragment.setPasswordEditVisible();

        }else{

            iAccountFragment.showVerificationError();

        }

    }

}
