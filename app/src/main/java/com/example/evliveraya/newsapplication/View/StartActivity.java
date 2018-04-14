package com.example.evliveraya.newsapplication.View;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.evliveraya.newsapplication.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button continueButton;
    private TextView welcome, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        continueButton = (Button) findViewById(R.id.launch_continue);
        welcome = (TextView) findViewById(R.id.launch_text1);
        description = (TextView) findViewById(R.id.launch_text2);

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        welcome.startAnimation(anim);

        Thread delayForDescription = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        delayForDescription.start();
        description.startAnimation(anim);

        Thread delayForButton = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        delayForButton.start();
        continueButton.startAnimation(anim);

        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.launch_continue) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
    }

    @Override
    public void onBackPressed() { finish(); }
}
