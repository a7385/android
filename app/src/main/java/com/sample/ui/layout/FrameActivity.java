package com.sample.ui.layout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

public class FrameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //框架layout
        setContentView(R.layout.activity_frame);
    }
}