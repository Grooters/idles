package io.github.grooters.idles.utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class SimpleObserver<T> implements Observer<T> {
    private T response;

    @Override
    public void onSubscribe(Disposable d) { }

    @Override
    public void onNext(T t) {
        response = t;
    }

    @Override
    public void onError(Throwable e) { }

    @Override
    public void onComplete() { }

    public abstract void handle(T response);
}
