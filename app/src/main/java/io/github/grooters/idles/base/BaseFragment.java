package io.github.grooters.idles.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import io.github.grooters.idles.view.activity.LoginActivity;

/**
 * Create by 李林浪 in 2019/6/12
 * Elegant Code...
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");

        View layout = inflater.inflate(getLayout(),null);

        initView(layout);

        return layout;

    }

    public abstract int getLayout();

    public abstract void initView(View view);

}
