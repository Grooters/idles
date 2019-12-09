package io.github.grooters.idles.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class LoadingWidget extends TextView{

    private SpannableString spanString;

    private RelativeSizeSpan sizeSpan;

    private Runnable runnable;

    private int index;

    private boolean b;

    public LoadingWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setVisibility(GONE);
        index = 0;
        b = false;
        spanString = new SpannableString("● ● ● ●");
        sizeSpan = new RelativeSizeSpan(1.8f);
        initAnimator();
    }

    private void initAnimator(){

        runnable = new Runnable() {
            int time = 800;
            @Override
            public void run() {
                while(b){
                    index = index + 2;
                    if( index == 8 ){
                        index = 0;
                        time = 800;
                    }else if( index == 2 ){
                        time = 800;
                    }else if( index == 4 ){
                        time = 800;
                    }else if( index == 6 ){
                        time = 800;
                    }
                    invalidate();
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

    }

    public void start(){
        b = true;
        setVisibility(VISIBLE);
        new Thread(runnable).start();
    }

    public void stop(){
        setVisibility(GONE);
        b = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("onAnimationUpdate", String.valueOf(index));
        if(b) spanString.setSpan(sizeSpan, index, index + 1 , Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(spanString);
    }
}
