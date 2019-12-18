package io.github.grooters.idles.net;

public interface ModelCallBack<T> {

    public void success(T data);

    public void failure(String message);

}
