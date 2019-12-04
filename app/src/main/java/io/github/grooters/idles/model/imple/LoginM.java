package io.github.grooters.idles.model.imple;
import	java.io.IOException;

import com.orhanobut.logger.Logger;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.model.ILoginM;
import io.github.grooters.idles.net.ModelCallBack;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.api.LoginApi;
import io.github.grooters.idles.net.ServerAddress;
import io.github.grooters.idles.utils.SimpleObserver;
import io.github.grooters.idles.view.fragment.inter.ILoginFragment;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginM implements ILoginM {

    private ILoginFragment iLoginFragment;
    private ILoginP iLoginP;
    private Disposable disposable;

    public LoginM(ILoginFragment iLoginFragment, ILoginP iLoginP){

        this.iLoginFragment = iLoginFragment;

        this.iLoginP = iLoginP;

    }

    @Override
    public void login(String number, String password, final ModelCallBack callBack) {

        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).login(number, password)
                .subscribeOn(Schedulers.io())
                // 这里要用io线程来实现第二个接口的请求访问
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(ResponseBody body) throws Exception {
                        String token = body.string();
                        Logger.d("token: " + token);
                        return Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getUser(token);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<User> (){
                    @Override
                    public void accept(User user) {
                        Logger.d(user.getName());
                        callBack.success(user);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onNext(User user) { }
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
    public void getUser(String token, final ModelCallBack callBack) {
        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getUser(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(new Consumer<User>() {
                @Override
                public void accept(User user) {
                    callBack.success(user);
                }
            })
            .subscribe(new Observer<User>() {
                @Override
                public void onSubscribe(Disposable d) {
                    disposable = d;
                }
                @Override
                public void onNext(User user) { }
                @Override
                public void onError(Throwable e) {
                    callBack.failure(e.getMessage());
                }
                @Override
                public void onComplete() { }
            });

    }

    @Override
    public void loginAsVisitor(final ModelCallBack callBack) {

        Logger.d("loginAsVisitor");
        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getVisitor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody body) throws IOException {
                        String token = body.string();
                        Logger.d("token:" + token);
                        callBack.success(token);
                    }
                })
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onNext(ResponseBody body) { }
                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        callBack.failure(e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        // Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $：
//                        disposable.dispose();
                    }
                });

    }

    @Override
    public void getCode(String email, final ModelCallBack callBack) {
        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).getCode(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody body) throws IOException {
                        String code = body.string();
                        Logger.d("code:" + code);
                        callBack.success(code);
                    }
                }).subscribe(new SimpleObserver<ResponseBody>() {
                    @Override
                    public void handle(ResponseBody response) { }
        });
    }

    @Override
    public void register(String email, String password, String code, final ModelCallBack callBack) {
        Retrofiter.getApi(LoginApi.class, ServerAddress.TEST_URL).register(email, password, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<User>() {
                    @Override
                    public void accept(User user) {
                        callBack.success(user);
                    }
                }).subscribe(new SimpleObserver<User>() {
                    @Override
                    public void handle(User user) { }
                });
    }
}
