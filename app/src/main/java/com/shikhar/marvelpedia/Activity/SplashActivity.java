package com.shikhar.marvelpedia.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shikhar.marvelpedia.BuildConfig;
import com.shikhar.marvelpedia.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static android.R.attr.password;

public class SplashActivity extends AppCompatActivity {

    private static String API_KEY = BuildConfig.API_KEY;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView)findViewById(R.id.key);
        tv.setText(API_KEY);

    }
}

//TODO

//splash activity, add photos no internet(image), no data found(when wrong text entered for search na)(image)         network_error(toast)
// picasso - error,placeholder(diff images)

//see glide notepad

//see retorofit me 100 limit dalne se data aa raha hai na??othewise 90,80 ....