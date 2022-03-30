package com.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LifecycleActivity extends AppCompatActivity {

    public static int count = 0;

    TextView m_TextView_onCreate;
    TextView m_TextView_onStart;
    TextView m_TextView_onResume;
    TextView m_TextView_onPause;
    TextView m_TextView_onStop;
    TextView m_TextView_onDestroy;
    TextView m_TextView_onRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        count++;

        TextView activity = findViewById(R.id.activity);
        activity.setText("activity:" + count);
        Button add = findViewById(R.id.addActivity);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifecycleActivity.this, LifecycleActivity.class);
                startActivity(intent);
            }
        });

        Button remove = findViewById(R.id.removeActivity);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        m_TextView_onCreate = findViewById(R.id.onCreate);
        m_TextView_onStart = findViewById(R.id.onStart);
        m_TextView_onResume = findViewById(R.id.onResume);
        m_TextView_onPause = findViewById(R.id.onPause);
        m_TextView_onStop = findViewById(R.id.onStop);
        m_TextView_onDestroy = findViewById(R.id.onDestroy);
        m_TextView_onRestart = findViewById(R.id.onRestart);


        m_TextView_onCreate.setText("onCreate: " + getTimeString());
        Log.d("LifecycleActivity", "onCreate: " + getTimeString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //當Activity變得可見時調用該函數
        m_TextView_onStart.setText("onStart: " + getTimeString());
        Log.d("LifecycleActivity", "onStart: " + getTimeString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        //把保存的資料拿回來使用
        m_TextView_onResume.setText("onResume: " + getTimeString());
        Log.d("LifecycleActivity", "onResume: " + getTimeString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //時把需要保存的資料保存
        m_TextView_onPause.setText("onPause: " + getTimeString());
        Log.d("LifecycleActivity", "onPause: " + getTimeString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Activity變得不可見時調用該方法
        m_TextView_onStop.setText("onStop: " + getTimeString());
        Log.d("LifecycleActivity", "onStop: " + getTimeString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //通常都拿來把onCreate()時的資料做釋放的動作
        m_TextView_onDestroy.setText("onDestroy: " + getTimeString());

        Log.d("LifecycleActivity", "onDestroy: " + getTimeString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //再次啟動之前將會調用該方法
        m_TextView_onRestart.setText("onRestart: " + getTimeString());
        Log.d("LifecycleActivity", "onRestart: " + getTimeString());
    }

    private String getTimeString() {
        String dateString = "";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
        try {
            dateString = format.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }
}
