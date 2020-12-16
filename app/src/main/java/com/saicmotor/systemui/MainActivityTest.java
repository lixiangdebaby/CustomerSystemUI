package com.saicmotor.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.saicmotor.systemui.utils.DropDownAnimationUtils;

public class MainActivityTest extends AppCompatActivity {

    private float mDensity;
    private LinearLayout mHiddenLayout;
    private int mHiddenViewMeasuredHeight;
    private ImageView mIv;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView((int) R.layout.activity_main_test);
        this.mHiddenLayout = (LinearLayout) findViewById(R.id.linear_hidden);
        this.mIv = (ImageView) findViewById(R.id.my_iv);
        float f = getResources().getDisplayMetrics().density;
        this.mDensity = f;
        float hiddenLayoutHeight = mHiddenLayout.getLayoutParams().height;
        Log.d("lixiang","hiddenLayoutHeight = "+hiddenLayoutHeight + " this.mDensity= "+this.mDensity);
        this.mHiddenViewMeasuredHeight = (int) (((double) (hiddenLayoutHeight)) + 0.5d);
    }

    public void onClick(View v) {
        if (this.mHiddenLayout.getVisibility() == View.GONE) {
            animateOpen(this.mHiddenLayout);
            animationIvOpen();
            return;
        }
        DropDownAnimationUtils.animateClose(this.mHiddenLayout);
        animationIvClose();
    }

    private void animateOpen(View v) {
        v.setVisibility(View.VISIBLE);
        DropDownAnimationUtils.createDropAnimator(v, 0, this.mHiddenViewMeasuredHeight).start();
    }

    private void animationIvOpen() {
        RotateAnimation animation = new RotateAnimation(0.0f, 180.0f, 1, 0.5f, 1, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        this.mIv.startAnimation(animation);
    }

    private void animationIvClose() {
        RotateAnimation animation = new RotateAnimation(180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        this.mIv.startAnimation(animation);
    }




}