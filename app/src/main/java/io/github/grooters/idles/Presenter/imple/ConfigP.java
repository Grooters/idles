package io.github.grooters.idles.Presenter.imple;

import android.content.Context;

import com.orhanobut.logger.Logger;

import io.github.grooters.idles.Presenter.IConfigP;
import io.github.grooters.idles.net.ServerAddress;
import io.github.grooters.idles.utils.Encrypter;
import io.github.grooters.idles.utils.FileIOer;
import io.github.grooters.idles.view.dialog.ConfigDialog;
import io.github.grooters.idles.view.dialog.inter.IConfigDilog;

public class ConfigP implements IConfigP {

    private IConfigDilog iConfigDilog;

    public static final String SERVER_URL = Encrypter.toBase64("serverUrl");

    public ConfigP(ConfigDialog configDialog){

        this.iConfigDilog =configDialog;

    }

    @Override
    public void setUrl(Context context, String localUrl, String netUrl, String key) {

        if(!key.equals("530412")){

            iConfigDilog.configFailure("授权密码错误，无法设置");

            return;

        }

        StringBuilder builder = new StringBuilder();

        StringBuilder reallyLocalUrl = new StringBuilder();

        if(!localUrl.equals("")){

            reallyLocalUrl.append("http://").append(localUrl).append(".natappfree.cc");

            ServerAddress.localUrl = reallyLocalUrl.toString();

            builder.append("localUrl-").append(reallyLocalUrl).append("|");


        } else if(!netUrl.equals("")){

            ServerAddress.netLocal = netUrl;

            builder.append("netUrl-").append(netUrl).append("|");

        }else{

            builder.append("localUrl-").append(localUrl).append("|").append("netUrl-").append(netUrl).append("|");
        }

        if(!FileIOer.delete(context, SERVER_URL)){

            Logger.d("未能成功删除服务器地址配置文件");

        }

        FileIOer.writeString(context, SERVER_URL, Encrypter.toBase64(builder.toString()));

        iConfigDilog.configSuccess(reallyLocalUrl.toString(), netUrl);

    }

}
