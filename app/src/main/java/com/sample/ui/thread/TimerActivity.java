package com.sample.ui.thread;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    private int[] phoneResourceArray = {R.drawable.phone_1, R.drawable.phone_2, R.drawable.phone_3, R.drawable.phone_4, R.drawable.phone_5};
    private int imageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        TextView countDownTimer = findViewById(R.id.countDownTimer);
        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CountDownTimer(60 * 1000, 1) {
                    @Override
                    public void onTick(long time) {
                        String str = String.format("倒數計時 ： %.3f", time / 1000.0);

                        countDownTimer.setText(str);
                    }

                    @Override
                    public void onFinish() {
                        countDownTimer.setText("倒數結束");
                    }
                }.start();
            }
        });

        ImageView phone = findViewById(R.id.phone);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                phone.setImageResource(phoneResourceArray[imageIndex % phoneResourceArray.length]);
                imageIndex++;
            }
        };

        new Timer().schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1 * 1000, 3 * 1000);
    }
}