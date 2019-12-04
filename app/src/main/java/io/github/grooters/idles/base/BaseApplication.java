package io.github.grooters.idles.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import io.github.grooters.idles.view.activity.LoginActivity;

public class BaseApplication extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Logger.addLogAdapter(new AndroidLogAdapter());

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);

        finish();

    }

}
