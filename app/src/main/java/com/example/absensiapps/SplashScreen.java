package com.example.absensiapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int SPLASH_DISPLAY_LENGTH = 3000;

        Animation topAnim, bottomAnim;
        TextView tv_welcome, come;

        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_splash_screen);
            getSupportActionBar().hide();

            topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

            tv_welcome = findViewById(R.id.tv_welcome);
            come = findViewById(R.id.tv_welcome2);

            tv_welcome.setAnimation(topAnim);
            come.setAnimation(bottomAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Startup.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);

}}