package com.shikhar.marvelpedia.Activity.Util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

//for animation in SplashActivity
public class Util {

    // when use animate view function you must implement this interface
    public interface AnimateView {
        public void onAnimationEnd();
    }

    public static void animateView(Context mContext, int animResource, View view, final AnimateView instance) {
        Animation fade0 = AnimationUtils.loadAnimation(mContext, animResource);
        view.startAnimation(fade0);
        fade0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                instance.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            //do nothing
            }
        });
    }
}