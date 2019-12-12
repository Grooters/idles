package io.github.grooters.idles.model.imple;

import com.orhanobut.logger.Logger;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Verification;
import io.github.grooters.idles.model.ILoginM;
import io.github.grooters.idles.net.ModelCallBack;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.api.LoginApi;
import io.github.grooters.idles.net.ServerAddress;
import io.github.grooters.idles.view.fragment.inter.ILoginFragment;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginM implements ILoginM {

    private ILoginFragment iLoginFragment;
    private ILoginP iLoginP;

    public LoginM(ILoginFragment iLoginFragment, ILoginP iLoginP){

        this.iLoginFragment = iLoginFragment;

        this.iLoginP = iLoginP;

    }

    @Override
    public void getUser(String number, String password, final ModelCallBack<User> callBack) {
        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getUser(number, password)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .doOnNext(new Consumer<User>() {
                 @Override
                 public void accept(User user) {
                     callBack.success(user);
                 }
             }).subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) { }
            @Override
            public void onNext(User user) { }
            @Override
            public void onError(Throwable e) {
                callBack.failure("请求错误");
            }
            @Override
            public void onComplete() { }
        });
    }


    @Override
    public void getTokenAsVisitor(final ModelCallBack<Token> callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getTokenAsVisitor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Token>() {
                    @Override
                    public void accept(Token token) {
                        callBack.success(token);
                    }
                })
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(Token token) { }
                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        callBack.failure(e.getMessage());
                    }
                    @Override
                    public void onComplete() { }
                });

    }

    @Override
    public void getVerification(String phoneNumber, final ModelCallBack<Verification> callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getVerification(phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Verification>() {
                    @Override
                    public void accept(Verification verification){
                        callBack.success(verification);
                    }
                })
                .subscribe(new Observer<Verification>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(Verification verifications) { }
                    @Override
                    public void onError(Throwable e) {
                        callBack.failure(e.getMessage());
                    }
                    @Override
                    public void onComplete() { }
                });

    }

    @Override
    public void setUser(String email, String password, final ModelCallBack<User> callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).setUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<User>() {
                    @Override
                    public void accept(User users) {
                        callBack.success(users);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(User users) { }
                    @Override
                    public void onError(Throwable e) {
                        callBack.failure(e.getMessage());
                    }
                    @Override
                    public void onComplete() { }
                });
    }
}
