package com.saicmotor.systemui.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class DropDownAnimationUtils {
    public static ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{start, end});
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = ((Integer) arg0.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    public static void animateClose(final View view) {
        ValueAnimator animator = createDropAnimator(view, view.getHeight(), 0);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

}
