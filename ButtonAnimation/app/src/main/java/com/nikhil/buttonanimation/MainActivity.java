package com.nikhil.buttonanimation;

import android.content.Intent;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final long TIME_OUT = 2000;
    private TextView tvPlus, tvMinus, tvCount;
    private LinearLayout linearLayout, llShow, llCount;
    private int count = 1;
    private Animation leftToRight;
    private Animation rightToLeft;
    private Handler handler;
    private boolean checkForHide;
    private Animation topToBottom;
    private Animation bottomToTop;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        leftToRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);

        tvPlus = (TextView) findViewById(R.id.tvPlus);
        tvMinus = (TextView) findViewById(R.id.tvMinus);
        tvCount = (TextView) findViewById(R.id.tvCount);

        linearLayout = (LinearLayout) findViewById(R.id.lladd);
        llShow = (LinearLayout) findViewById(R.id.show);

        llCount = (LinearLayout) findViewById(R.id.llCount);


        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;
                tvCount.setText(String.valueOf(count));
                scaleView(tvCount, 1.5f, 1f);
//                checkForHide = false;
//                scaleView(tvPlus, 1.2f, 1f);
                RunAd();
            }
        });

        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                checkForHide = false;
                count = count - 1;
                tvCount.setText(String.valueOf(count));
                scaleView(tvCount, 1.5f, 1f);
//                scaleView(tvMinus, 1.2f, 1f);
                RunAd();
            }
        });


        llShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                tvPlus.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.VISIBLE);
                llCount.setVisibility(View.VISIBLE);
                tvPlus.startAnimation(leftToRight);
                tvMinus.startAnimation(rightToLeft);
                llShow.setVisibility(View.GONE);
                llShow.startAnimation(topToBottom);
                RunAd();
            }
        });


    }


    private void RunAd() {
        handler = new Handler();

        handler.removeCallbacksAndMessages(null);
        if (runnable != null)
            handler.removeCallbacks(runnable);

        runnable = new Runnable() {
            @Override
            public void run() {
                checkForHide();
            }
        };
        handler.postDelayed(runnable, TIME_OUT);

    }

    private void checkForHide() {

        llShow.startAnimation(bottomToTop);
        tvMinus.startAnimation(rightToLeft);
        tvPlus.startAnimation(leftToRight);
        llShow.setVisibility(View.VISIBLE);
        tvPlus.setVisibility(View.GONE);
        tvMinus.setVisibility(View.GONE);
        llCount.setVisibility(View.GONE);


    }


    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(200);
        anim.setStartTime(200);
        v.startAnimation(anim);
    }
}
