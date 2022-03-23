package com.sample.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

public class ShareActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        TextView shareText = findViewById(R.id.shareText);

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        shareText.setText(text);
    }
}