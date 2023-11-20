package com.example.duan1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen_Activity extends AppCompatActivity {

    TextView tv;
    Animation set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        set = AnimationUtils.loadAnimation(this, R.anim.set);
        tv = findViewById(R.id.tv);
        tv.startAnimation(set);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}