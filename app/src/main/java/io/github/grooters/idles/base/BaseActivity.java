package io.github.grooters.idles.base;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import io.github.grooters.idles.R;


/**
 * Create by 李林浪 in 2019/6/12
 * Elegant Code...
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Handler timerHandler;

    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        Fragment fragment = createFragment();

        if( fragment != null ){

            getSupportFragmentManager().beginTransaction().replace(R.id.login_frame_fragment_container,fragment).commit();

        }

        if( delayJump() != null ){

            timerHandler = new Handler();

            runnable = new Runnable() {
                @Override
                public void run() {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.login_frame_fragment_container,delayJump())
                            .commit();

                }
            };

            timerHandler.postDelayed(runnable,3000);

        }

    }

    public abstract Fragment createFragment();

    public abstract Fragment delayJump();


    public void setFullScreen(boolean isFull){

        if( isFull ){

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

        }else{

            getWindow().clearFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN );

        }

    }

    public void setTitleBarTransaction(){

        Window window = getWindow();

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        window.setStatusBarColor(Color.TRANSPARENT);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if( timerHandler !=null ){

            timerHandler.removeCallbacks(runnable);

        }

    }

    public abstract int getLayout();
}
