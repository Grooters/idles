package io.github.grooters.idles.net;

import io.github.grooters.idles.base.BaseBean;

public interface ModelCallBack<T> {

    public void success(T data);

    public void failure(String message);

}
