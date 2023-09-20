package com.example.breathingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breathingapp.util.Prefs;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.MessageFormat;

public class BreathingActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView breathsTxt, timeTxt, sessionTxt, guideTxt;
    private Button startButton;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);
        imageView = findViewById(R.id.LotusImage);
        breathsTxt = findViewById(R.id.BreathsTakenTxt);
        timeTxt = findViewById(R.id.LastBreathTxt);
        sessionTxt = findViewById(R.id.TodayMinutesTxt);
        guideTxt = findViewById(R.id.GuideTxt);
        prefs = new Prefs(this);

        startIntroAnimation();

        sessionTxt.setText(MessageFormat.format("{0} min today", prefs.getSessions()));
        breathsTxt.setText(MessageFormat.format("{0} Breaths", prefs.getBreaths()));
        timeTxt.setText(prefs.getDate());

        startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
    }
    private void startIntroAnimation(){
        ViewAnimator
                .animate(guideTxt)
                .scale(0,1)
                .duration(1500)
                .onStart(new AnimationListener.Start(){
                    @Override
                    public void onStart(){
                        guideTxt.setText("Breathe");
                    }
                })
                .start();
    }
    private void startAnimation(){
        ViewAnimator
                .animate(imageView)
                .alpha(0,1)
                .onStart(new AnimationListener.Start(){
                    @Override
                    public void onStart(){
                        guideTxt.setText("Inhale... Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.5f, 0.02f)
                .rotation(360)
                .repeatCount(5)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop(){
                    @Override
                    public void onStop(){
                        guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 1);
                        prefs.setDate(System.currentTimeMillis());

                        //Refresh Activity
                        new CountDownTimer(2000, 1000){

                            @Override
                            public void onTick(long l) {
                                //put code to show ticking
                            }

                            @Override
                            public void onFinish() {
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                startActivity(new Intent(getApplicationContext(), BreathingActivity.class));
                                finish();
                            }
                        }.start();
                    }
                })
                .start();
    }
}