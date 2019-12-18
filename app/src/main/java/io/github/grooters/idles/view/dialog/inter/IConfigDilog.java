package io.github.grooters.idles.view.dialog.inter;

public interface IConfigDilog {

    void configFailure(String message);

    void configSuccess(String localUrl, String netUrl);

}
