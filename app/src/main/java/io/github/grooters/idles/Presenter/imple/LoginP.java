package io.github.grooters.idles.Presenter.imple;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
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
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.LoginActivity;
import io.github.grooters.idles.view.activity.inter.ILoginActivity;
import io.github.grooters.idles.view.fragment.AccountFragment;
import io.github.grooters.idles.view.fragment.LoginFragment;
import io.github.grooters.idles.view.fragment.inter.IAccountFragment;
import io.github.grooters.idles.view.fragment.inter.ILoginFragment;

public class LoginP implements ILoginP {

    private ILoginFragment iLoginFragment;

    private IAccountFragment iAccountFragment;

    private ILoginActivity iLoginActivity;

    private Verification verification;

    private ILoginM iLoginM;

    private final String ACCOUNT_INFO = Encrypter.md5("accountInfo");

    private final String ACCOUNT_USER = Encrypter.md5("accountUser");

    private final String ACCOUNT_VISITOR = Encrypter.md5("accountVisitor");

    private boolean isRememberAccount;

    private User user;

    private int i = 60;

    private int j = 2;

    public LoginP(LoginFragment loginFragment){

        iLoginFragment = loginFragment;

        iLoginM = new LoginM(iLoginFragment, this);

    }

    public LoginP(AccountFragment accountFragment){

        iAccountFragment = accountFragment;

        iLoginM = new LoginM(iLoginFragment, this);

    }

    public LoginP(LoginActivity loginActivity){

        iLoginActivity = loginActivity;

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
                    case ResponseCode.LOGIN_SUCCESS:
                        user = data.getData().getUser();

                        if( isRememberAccount)

                            FileIOer.writeString(context, ACCOUNT_INFO, Encrypter.toBase64(Jsoner.toJson(user)));

                        iLoginFragment.stopLading();

                        FileIOer.writeString(context, ACCOUNT_USER, Encrypter.toBase64(Jsoner.toJson(user)));

                        iLoginFragment.loginSuccess();
                        return;
                }

                Toaster.shortShow(context, "测试过程，需要更改接口响应码");

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

                int code = tokens.getCode();

                switch (code){
                    case ResponseCode.LOGIN_SUCCESS:

                        iLoginFragment.loginSuccessAsVisitor();

                        FileIOer.writeString(context, ACCOUNT_VISITOR, Encrypter.toBase64(Jsoner.toJson(token)));

                        return;

                    case ResponseCode.ACCOUNT_NON:

                        iLoginFragment.loginFailureAsVisitor();
                        return;
                }

                Toaster.shortShow(context, "测试过程，需要更改接口响应码");

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

        Logger.d("从账号记录配置文件中读出来的字符串："+Encrypter.fromBase64(json));

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

            iLoginFragment.rememberAccount();

        }else{

            iLoginFragment.forgetAccount();

            if(!FileIOer.delete(context, ACCOUNT_INFO)){
                Logger.d("未能成功删除账户记录配置文件");
            }

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

    // 注册手机账号
    @Override
    public void getVerification(final Context context, String email) {

        if(i != 60){

            iAccountFragment.showVerificationError("需要等待"+i+"秒后才能再次获得验证码");

            return;

        }

        iLoginM.getVerification(email, new ModelCallBack<Verification>() {
            @Override
            public void success(BaseBean<Verification> verifications) {

                int code = verifications.getCode();

                switch (code){

                    case ResponseCode.GET_VERIFICATION_SUCCESS:

                        new VerificationThread().start();

                        verification = verifications.getData();

                        iAccountFragment.showSuccess(verifications.getDesc());

                        return;

                    case ResponseCode.GET_VERIFICATION_FAILURE:

                        // 业务逻辑问题
                        iAccountFragment.showFailure("验证码获取失败");

                        return;

                }

                Toaster.shortShow(context, "测试过程，需要更改接口响应码");
            }

            @Override
            public void failure(String message) {

                verification = null;

                // 接口调用问题
                iAccountFragment.showFailure("验证码获取失败");

            }
        });

    }

    @Override
    public void verify(String str) {

        if(verification!= null && verification.getVerification().equals(str)){

            iAccountFragment.setPasswordEditVisible();

        }else{

            iAccountFragment.showVerificationError("验证失败");

        }

    }

    @Override
    public boolean endActivity(Context context, int keyCode, KeyEvent event) {

        Logger.d(j);

        new EndThread().start();

        if(keyCode == KeyEvent.KEYCODE_BACK && j != 1){

            Toaster.shortShow(context, "再按一次退出程序");

            --j;

        }else if(keyCode == KeyEvent.KEYCODE_BACK){

            iLoginActivity.destroy();

        }

        return false;

    }

    // 倒计时再次获取验证码
    class VerificationThread extends Thread{

        @Override
        public void run() {

            super.run();

            while(--i >= 0){

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }

            HandleUi handleUi = new HandleUi(Looper.getMainLooper());

            Message message = Message.obtain();

            message.obj = iAccountFragment;

            handleUi.sendMessage(message);

            i = 60;

        }
    }

    // 倒计时按两次返回退出程序
    class EndThread extends Thread {

        int i = 3;

        @Override
        public void run() {

            super.run();

            while(--i >= 0){

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }

            j = 2;

        }
    }

    static class HandleUi extends Handler {

        private IAccountFragment iAccountFragment;

        HandleUi(@NonNull Looper looper) {

            super(looper);

        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            iAccountFragment = (IAccountFragment) msg.obj;

            super.handleMessage(msg);

            iAccountFragment.initVerificationTextView();

        }
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
