package io.github.grooters.idles.view.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.BaseActivity;
import io.github.grooters.idles.view.fragment.LoginFragment;

/**
 * Create by 李林浪 in 2019/6/21
 * Elegant Code...
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 在super.onCreate(savedInstanceState);后调用会报 Unable to instantiate appComponentFactory
        setFullScreen(true);

        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: sendEmptyMessage_later");

    }

    @Override
    public Fragment createFragment() {

        return new LoginFragment();

    }

    // 延时跳转，用于启动页面
    @Override
    public Fragment delayJump() {

        return null;
    }


    @Override
    public int getLayout() {

        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
