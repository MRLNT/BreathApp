package com.mrlnt.breathapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;

    private View innerCircleView;

    private Animation animationInhaleText;
    private Animation animationExhaleText;
    private Animation animationInhaleInnerCircle;
    private Animation animationExhaleInnerCircle;

    private final Handler handler = new Handler();

    private final int inhaleDuration = 6000;
    private final int exhaleDuration = 6000;
    private final int holdDuration = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.textView);
        innerCircleView = findViewById(R.id.circle);

        statusText.setText("Ketuk untuk Memulai");

        prepareAnimations();

        innerCircleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInhale();
                innerCircleView.setOnClickListener(null);
            }
        });
    }

    private void prepareAnimations() {
        animationInhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_inhale);
        animationInhaleText.setDuration(inhaleDuration);
        animationInhaleText.setFillAfter(true);
        animationInhaleText.setAnimationListener(inhaleAnimationListener);
        animationInhaleInnerCircle = AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_inhale);
        animationInhaleInnerCircle.setDuration(inhaleDuration);
        animationInhaleInnerCircle.setFillAfter(true);

        animationExhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_exhale);
        animationExhaleText.setDuration(exhaleDuration);
        animationExhaleText.setAnimationListener(exhaleAnimationListener);
        animationExhaleInnerCircle = AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_exhale);
        animationExhaleInnerCircle.setDuration(exhaleDuration);
        animationExhaleInnerCircle.setFillAfter(true);
    }

    private void startInhale(){
        statusText.setText("INHALE");
        statusText.startAnimation(animationInhaleText);
        innerCircleView.startAnimation(animationInhaleText);
        innerCircleView.startAnimation(animationInhaleInnerCircle);
    }

    private final Animation.AnimationListener inhaleAnimationListener = new Animation.AnimationListener(){
        @Override
        public void onAnimationEnd(Animation animation){
            statusText.setText("HOLD");
            handler.postDelayed(MainActivity.this::startExhale, holdDuration);
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    private void startExhale(){
        statusText.setText("EXHALE");
        statusText.startAnimation(animationExhaleText);
        innerCircleView.startAnimation(animationExhaleText);
        innerCircleView.startAnimation(animationExhaleInnerCircle);
    }

    private final Animation.AnimationListener exhaleAnimationListener = new Animation.AnimationListener(){
        @Override
        public void onAnimationEnd(Animation animation){
            statusText.setText("HOLD");
            handler.postDelayed(() -> {
                statusText.setText("Ketuk untuk Memulai");
                innerCircleView.setOnClickListener(v -> {
                    startInhale();
                    innerCircleView.setOnClickListener(null);
                });
            }, holdDuration);
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };
}