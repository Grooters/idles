package io.github.grooters.idles.model.imple;

import com.orhanobut.logger.Logger;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.base.BaseBean;
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Verification;
import io.github.grooters.idles.model.ILoginM;
import io.github.grooters.idles.net.ModelCallBack;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.api.LoginApi;
import io.github.grooters.idles.net.ServerAddress;
import io.github.grooters.idles.view.fragment.inter.ILoginFragment;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginM implements ILoginM {

    private ILoginFragment iLoginFragment;
    private ILoginP iLoginP;

    public LoginM(ILoginFragment iLoginFragment, ILoginP iLoginP){

        this.iLoginFragment = iLoginFragment;

        this.iLoginP = iLoginP;

    }

    @Override
    public void getUserNoToken(String number, String password, final ModelCallBack<User> callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getToken(number, password)
                .subscribeOn(Schedulers.io())
                // 这里要用io线程来实现第二个接口的请求访问
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseBean<Token>, ObservableSource<BaseBean<User>>>() {
                    @Override
                    public ObservableSource<BaseBean<User>> apply(BaseBean<Token> tokens) {
                        Logger.d(tokens.getData().getToken());
                        return Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getUser(tokens.getData().getToken());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseBean<User>> (){
                    @Override
                    public void accept(BaseBean<User> users) {
                        Logger.d(users.getDesc());
                        callBack.success(users);
                    }
                })
                .subscribe(new Observer<BaseBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(BaseBean<User> user) { }
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
    public void getToken(final ModelCallBack<Token> callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getTokenByVisitor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseBean<Token>>() {
                    @Override
                    public void accept(BaseBean<Token> tokens) {
                        callBack.success(tokens);
                    }
                })
                .subscribe(new Observer<BaseBean<Token>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(BaseBean<Token> tokens) { }
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
                .doOnNext(new Consumer<BaseBean<Verification>>() {
                    @Override
                    public void accept(BaseBean<Verification> verifications){
                        callBack.success(verifications);
                    }
                })
                .subscribe(new Observer<BaseBean<Verification>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(BaseBean<Verification> verifications) { }
                    @Override
                    public void onError(Throwable e) {
                        callBack.failure(e.getMessage());
                    }
                    @Override
                    public void onComplete() { }
                });

    }

    @Override
    public void getUser(String token, final ModelCallBack<User> callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getUser(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(new Consumer<BaseBean<User>>() {
                @Override
                public void accept(BaseBean<User> user) {
                    callBack.success(user);
                }
            })
            .subscribe(new Observer<BaseBean<User>>() {
                @Override
                public void onSubscribe(Disposable d) { }
                @Override
                public void onNext(BaseBean<User> user) { }
                @Override
                public void onError(Throwable e) {
                    callBack.failure(e.getMessage());
                }
                @Override
                public void onComplete() { }
            });

    }

    @Override
    public void setUser(String phoneNumber, String password, final ModelCallBack<User> callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).register(phoneNumber, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseBean<User>>() {
                    @Override
                    public void accept(BaseBean<User> users) {
                        callBack.success(users);
                    }
                })
                .subscribe(new Observer<BaseBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(BaseBean<User> users) { }
                    @Override
                    public void onError(Throwable e) {
                        callBack.failure(e.getMessage());
                    }
                    @Override
                    public void onComplete() { }
                });
    }
}
