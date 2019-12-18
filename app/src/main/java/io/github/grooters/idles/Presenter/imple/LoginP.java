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
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Verification;
import io.github.grooters.idles.model.ILoginM;
import io.github.grooters.idles.model.imple.LoginM;
import io.github.grooters.idles.net.ModelCallBack;
import io.github.grooters.idles.net.ResponseCode;
import io.github.grooters.idles.net.ServerAddress;
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

    private int backCount = 0;

    private int configCount = 0;

    private int verifyTime = 0;

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

        if(number.equals("") || password.equals("")){

            Toaster.shortShow(context, "账户或密码不能为空");

            return;

        }

        iLoginFragment.startLading();

        iLoginM.getUser(number, password, new ModelCallBack<User>() {

            @Override
            public void success(User data) {

                Logger.d(data.getCode());

                switch (data.getCode()){
                    case ResponseCode.PASS_ERROR:
                        Toaster.shortShow(context, "账户或密码错误");
                        iLoginFragment.stopLading();
                        return;
                    case ResponseCode.ACCOUNT_NON:
                        Toaster.shortShow(context, "账户不存在");
                        iLoginFragment.stopLading();
                        return;
                    case ResponseCode.UNKNOWN:
                        Toaster.shortShow(context, "未知错误");
                        iLoginFragment.stopLading();
                        return;
                    case ResponseCode.LOGIN_SUCCESS:
                        user = data;

                        if( isRememberAccount)

                            FileIOer.writeString(context, ACCOUNT_INFO, Encrypter.toBase64(Jsoner.toJson(user)));

                        iLoginFragment.stopLading();

                        FileIOer.writeString(context, ACCOUNT_USER, Encrypter.toBase64(Jsoner.toJson(user)));

                        iLoginFragment.loginSuccess();
                        return;
                }

                iLoginFragment.stopLading();

                Toaster.shortShow(context, "测试过程，需要更改接口响应码");

            }

            @Override
            public void failure(String message) {

                iLoginFragment.stopLading();

                Toaster.shortShow(context, message);

            }

        });


    }

    @Override
    public void loginAsVisitor(final Context context) {
        // 拿到临时访问令牌
        iLoginM.getTokenAsVisitor(new ModelCallBack<Token>() {
            @Override
            public void success(Token tokens) {

                String token = tokens.getToken();

                int code = tokens.getCode();

                switch (code){
                    case ResponseCode.GET_TOKEN_SUCCESS:

                        iLoginFragment.loginSuccessAsVisitor();

                        FileIOer.writeString(context, ACCOUNT_VISITOR, Encrypter.toBase64(Jsoner.toJson(token)));

                        return;

                    case ResponseCode.GET_TOKEN_FAILURE:

                        Toaster.shortShow(context, "令牌获取失败");
                        return;
                }

                Toaster.shortShow(context, "测试过程，需要更改接口响应码");

            }

            @Override
            public void failure(String message) {

                Toaster.shortShow(context, "登录出错");

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
    public void initServerUrl(Context context) {

        if(!FileIOer.isExit(context, ConfigP.SERVER_URL)){

            return;
        }

        String serverUrl = Encrypter.fromBase64(FileIOer.readString(context, ConfigP.SERVER_URL));

        Logger.d(serverUrl);

        String[] url = serverUrl.split("\\|");

        if(url.length == 2){

            ServerAddress.localUrl = serverUrl.split("\\|")[0].split("-")[1];

            ServerAddress.netLocal = serverUrl.split("\\|")[1].split("-")[1];

        }else{

            Logger.d(serverUrl.split("\\|")[0]);

            ServerAddress.localUrl = serverUrl.split("\\|")[0].split("-")[1];

        }

        Logger.d(ServerAddress.localUrl + ":" + ServerAddress.netLocal);

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


    // 注册账号获取验证码
    @Override
    public void getVerification(final Context context, String email) {

        if(email.equals("") ){

            iAccountFragment.showFailure("邮箱地址不能为空");

            return;

        }


        if(verifyTime != 0){

            iAccountFragment.showFailure("需要等待"+verifyTime+"秒后才能再次获得验证码");

            return;

        }

        iLoginM.getVerification(email, new ModelCallBack<Verification>() {
            @Override
            public void success(Verification data) {

                int code = data.getCode();

                switch (code){

                    case ResponseCode.GET_VERIFICATION_SUCCESS:

                        new VerificationThread().start();

                        verification = data;

                        iAccountFragment.showVerificationSuccess(data.getMessage());

                        return;

                    case ResponseCode.GET_VERIFICATION_FAILURE:

                        // 业务逻辑问题
                        iAccountFragment.showFailure(data.getMessage());

                        return;

                }

                Toaster.shortShow(context, "测试过程，需要更改接口响应码");
            }

            @Override
            public void failure(String message) {

                verification = null;

                // 接口调用问题
                iAccountFragment.showFailure("验证码获取出错");

            }
        });

    }

    @Override
    public void verify(String str) {

        if(verification!= null && verification.getVerification().equals(str)){

            iAccountFragment.setPasswordEditVisible();

        }else{

            iAccountFragment.showFailure("验证失败");

        }

    }

    @Override
    public void register(final Context context, int type, String email, String password, String passwordSend) {

        if(!password.equals(passwordSend)){

            iAccountFragment.showFailure("两次密码输入不一致");

        }

        if(type == LoginFragment.REGISTER_ACCOUNT_FRAGMENT) {

            iAccountFragment.setRegisterButtonText("注册");

            // 注册账号
            iLoginM.setUser(email, password, new ModelCallBack<User>() {
                @Override
                public void success(User data) {

                    switch (data.getCode()) {

                        case ResponseCode.REGISTER_SUCCESS:

                            iAccountFragment.showRegisterSuccess(data.getMessage());

                            return;

                        case ResponseCode.REGISTER_FAILURE:

                            iAccountFragment.setPasswordVisibleGone();

                            iAccountFragment.showFailure(data.getMessage());

                            return;

                    }

                    iAccountFragment.showFailure("处于测试阶段，需要更改响应码");

                }

                @Override
                public void failure(String message) {

                    iAccountFragment.setPasswordVisibleGone();

                    iAccountFragment.showFailure("账号注册出错");

                }
            });

        }
        else if (type == LoginFragment.FIND_PASSWORD_FRAGMENT){

            iAccountFragment.setRegisterButtonText("找回");


            // 找回密码
            iLoginM.setUser(email, password, new ModelCallBack<User>() {
                @Override
                public void success(User data) {

                    Toaster.shortShow(context, "新密码设置成功");

                }

                @Override
                public void failure(String message) {

                    Toaster.shortShow(context, "密码找回出错");

                }
            });
        }

    }

    @Override
    public boolean endActivity(Context context, int keyCode, KeyEvent event) {

        Logger.d(++backCount);

        new EndThread().start();

        if(keyCode == KeyEvent.KEYCODE_BACK && backCount == 1){

            Toaster.shortShow(context, "再按一次退出程序");

            ++backCount;

        }else if(keyCode == KeyEvent.KEYCODE_BACK){

            iLoginActivity.destroy();

        }

        return false;

    }

    @Override
    public void config() {

        new ConfigThread().start();

        ++configCount;

        if(configCount == 5){

            iLoginFragment.showConfigDialog();

            configCount = 0;

        }

    }

    // 倒计时再次获取验证码
    class VerificationThread extends Thread{

        @Override
        public void run() {

            super.run();

            verifyTime = 60;

            while(verifyTime-- > 0){

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

        }
    }

    // 倒计时按两次返回退出程序
    class EndThread extends Thread {

        int time = 0;

        @Override
        public void run() {

            super.run();

            while(time++ < 2){

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }

            backCount = 0;

        }
    }

    class ConfigThread extends Thread {

        int time = 0;

        @Override
        public void run() {

            super.run();

            while(time++ < 2){

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }

            configCount = 0;

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
