package com.shikhar.marvelpedia.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.shikhar.marvelpedia.Activity.Util.Util;
import com.shikhar.marvelpedia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Util.AnimateView {

    @BindView(R.id.activity_splash)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // animate container of LinearLayout
        Util.animateView(this, R.anim.fade_in_enter, linearLayout, this);
    }

    @Override
    public void onAnimationEnd() {
        // When animation ends, start the MainActivity and remove SplashActivity from the Task Stack
        startActivity(new Intent(SplashActivity.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}


