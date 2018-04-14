package com.example.evliveraya.newsapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.evliveraya.newsapplication.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.splash_logo);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        logo.startAnimation(anim);
        final Intent intent = new Intent(this, StartActivity.class);
        Thread delay = new Thread() {
          public void run() {
              try {
                  sleep(2500);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              } finally {
                  startActivity(intent);
                  finish();
              }
          }
        };
        delay.start();
    }
}
