package io.github.grooters.idles.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import androidx.fragment.app.Fragment;
import io.github.grooters.idles.Presenter.ILoginP;
import io.github.grooters.idles.Presenter.imple.LoginP;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.BaseActivity;
import io.github.grooters.idles.base.BaseFragment;
import io.github.grooters.idles.view.activity.inter.ILoginActivity;
import io.github.grooters.idles.view.fragment.LoginFragment;

/**
 * Create by 李林浪 in 2019/6/21
 * Elegant Code...
 */
public class LoginActivity extends BaseActivity implements ILoginActivity {

    private static final String TAG = "LoginActivity";

    private ILoginP iLoginP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 在super.onCreate(savedInstanceState);后调用会报 Unable to instantiate appComponentFactory
        setFullScreen(true);

        super.onCreate(savedInstanceState);

        iLoginP = new LoginP(this);

    }

    @Override
    public Fragment createFragment() {

        return new LoginFragment();

    }

    @Override
    public void replaceFragment(BaseFragment fragment, String name) {

        getSupportFragmentManager().beginTransaction().replace(R.id.login_frame_fragment_container, fragment, name).commit();

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return iLoginP.endActivity(this, keyCode, event);

    }

    @Override
    public void destroy() {

        finish();

    }

    @Override
    public void startLading() {

    }

    @Override
    public void stopLading() {

    }
}
