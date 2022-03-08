package com.sample.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sample.ui.layout.ConstraintActivity;
import com.sample.ui.layout.FrameActivity;
import com.sample.ui.layout.LinearActivity;
import com.sample.ui.layout.RelativeActivity;
import com.sample.ui.layout.TableActivity;
import com.sample.ui.view.ImageViewActivity;
import com.sample.ui.view.SpinnerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button frameBtn = findViewById(R.id.frame);
        frameBtn.setOnClickListener(onClickListener);

        Button linearBtn = findViewById(R.id.linear);
        linearBtn.setOnClickListener(onClickListener);

        Button relativeBtn = findViewById(R.id.relative);
        relativeBtn.setOnClickListener(onClickListener);

        Button tableBtn = findViewById(R.id.table);
        tableBtn.setOnClickListener(onClickListener);

        Button constraintBtn = findViewById(R.id.constraint);
        constraintBtn.setOnClickListener(onClickListener);

        Button textviewBtn = findViewById(R.id.textview);
        textviewBtn.setOnClickListener(onClickListener);

        Button editTextBtn = findViewById(R.id.editText);
        editTextBtn.setOnClickListener(onClickListener);

        Button spinnerBtn = findViewById(R.id.spinner);
        spinnerBtn.setOnClickListener(onClickListener);

        Button listViewBtn = findViewById(R.id.listView);
        listViewBtn.setOnClickListener(onClickListener);

        Button imageViewBtn = findViewById(R.id.imageView);
        imageViewBtn.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.frame:
                    intent.setClass(MainActivity.this, FrameActivity.class);
                    break;
                case R.id.linear:
                    intent.setClass(MainActivity.this, LinearActivity.class);
                    break;
                case R.id.relative:
                    intent.setClass(MainActivity.this, RelativeActivity.class);
                    break;
                case R.id.table:
                    intent.setClass(MainActivity.this, TableActivity.class);
                    break;
                case R.id.constraint:
                    intent.setClass(MainActivity.this, ConstraintActivity.class);
                    break;
                case R.id.textview:
                    intent.setClass(MainActivity.this, TextView.class);
                    break;
                case R.id.editText:
                    intent.setClass(MainActivity.this, EditText.class);
                    break;
                case R.id.spinner:
                    intent.setClass(MainActivity.this, SpinnerActivity.class);
                    break;
                case R.id.listView:
                    intent.setClass(MainActivity.this, ListActivity.class);
                    break;
                case R.id.imageView:
                    intent.setClass(MainActivity.this, ImageViewActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };
}