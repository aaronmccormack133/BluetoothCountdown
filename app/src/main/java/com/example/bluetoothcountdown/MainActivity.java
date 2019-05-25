package com.example.bluetoothcountdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView mTextViewCountDown;
    private Button mButtonStart;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRun;

    private long mTimeLeft = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonStart = findViewById(R.id.button_start);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mTimerRun){
                    pauseTimer();
                }
                else{
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetTimer();
            }
        });

        updateCountDown();
    }

    public void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeft = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                mTimerRun = false;
                mButtonStart.setText("Start");
                mButtonStart.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRun = true;
        mButtonStart.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    public void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRun = false;
        mButtonStart.setText("Start");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    public void resetTimer(){
        mTimeLeft = START_TIME_IN_MILLIS;
        updateCountDown();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStart.setVisibility(View.VISIBLE);
    }

    public void updateCountDown(){
        int mins = (int) (mTimeLeft / 1000) / 60;
        int secs = (int) (mTimeLeft / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
        mTextViewCountDown.setText(timeLeft);
    }
}
