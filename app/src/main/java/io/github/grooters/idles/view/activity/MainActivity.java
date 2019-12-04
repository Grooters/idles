package io.github.grooters.idles.view.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import io.github.grooters.idles.R;
import io.github.grooters.idles.base.BaseActivity;
import io.github.grooters.idles.view.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setFullScreen(false);

//        setTitleBarTransaction();

        super.onCreate(savedInstanceState);

        manager = getSupportFragmentManager();

        Log.d(TAG, "onCreate: sendEmptyMessage_later");

    }


    @Override
    public Fragment createFragment() {

        return new MainFragment();

    }

    @Override
    public Fragment delayJump() {

        return null;

    }

    @Override
    public int getLayout() {

        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
