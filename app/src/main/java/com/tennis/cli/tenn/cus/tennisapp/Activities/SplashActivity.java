package com.tennis.cli.tenn.cus.tennisapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tennis.cli.tenn.cus.tennisapp.R;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView animationViewLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // tO SHOW ACTIVITY ON FULL SCREEN //
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animationViewLottie = findViewById(R.id.animation_view);

        animationViewLottie.setAnimation(R.raw.tennis_loading);
        animationViewLottie.playAnimation();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    sleep(5000);

                    gotoMainActivity();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    private void gotoMainActivity() {

        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }


}