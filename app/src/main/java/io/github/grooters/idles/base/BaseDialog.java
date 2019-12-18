package io.github.grooters.idles.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public abstract class BaseDialog extends DialogFragment {

    private static final String TAG = "BaseFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");

        View layout = inflater.inflate(getLayout(),null);

        initView(layout);

        return layout;

    }

    public void setTransaction(){

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public abstract int getLayout();

    public abstract void initView(View view);

}
