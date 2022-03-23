package com.sample.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sample.ui.activity.IntentActivity;
import com.sample.ui.activity.LifecycleActivity;
import com.sample.ui.data.SharedPreferencesActivity;
import com.sample.ui.layout.ConstraintActivity;
import com.sample.ui.layout.FrameActivity;
import com.sample.ui.layout.LinearActivity;
import com.sample.ui.layout.RelativeActivity;
import com.sample.ui.layout.TableActivity;
import com.sample.ui.view.EditTextActivity;
import com.sample.ui.view.ImageViewActivity;
import com.sample.ui.view.ListViewActivity;
import com.sample.ui.view.SpinnerActivity;
import com.sample.ui.view.TextViewActivity;
import com.sample.ui.view.ViewActivity;

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

        Button viewBtn = findViewById(R.id.viewBtn);
        viewBtn.setOnClickListener(onClickListener);

        Button lifeCycleBtn = findViewById(R.id.lifeCycle);
        lifeCycleBtn.setOnClickListener(onClickListener);

        Button intentBtn = findViewById(R.id.intent);
        intentBtn.setOnClickListener(onClickListener);

        Button sharedPreferencesBtn = findViewById(R.id.sharedPreferences);
        sharedPreferencesBtn.setOnClickListener(onClickListener);


        TextView screenInfo = findViewById(R.id.screenInfo);
        //取得螢幕寬高密度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenInfo.setText("screenWidth:" + dm.widthPixels + "  "
                + "screenHeight:" + dm.heightPixels + "\n"
                + "xdp(寬):" + dm.xdpi + "  "
                + "ydp(高):" + dm.ydpi + "\n"
                + "density:" + dm.density + "  "
                + "scaledDensity:" + dm.scaledDensity + "  "
                + "dpi:" + dm.densityDpi);

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
                    intent.setClass(MainActivity.this, TextViewActivity.class);
                    break;
                case R.id.editText:
                    intent.setClass(MainActivity.this, EditTextActivity.class);
                    break;
                case R.id.spinner:
                    intent.setClass(MainActivity.this, SpinnerActivity.class);
                    break;
                case R.id.listView:
                    intent.setClass(MainActivity.this, ListViewActivity.class);
                    break;
                case R.id.imageView:
                    intent.setClass(MainActivity.this, ImageViewActivity.class);
                    break;
                case R.id.viewBtn:
                    intent.setClass(MainActivity.this, ViewActivity.class);
                    break;
                case R.id.lifeCycle:
                    intent.setClass(MainActivity.this, LifecycleActivity.class);
                    break;
                case R.id.intent:
                    intent.setClass(MainActivity.this, IntentActivity.class);
                    break;
                case R.id.sharedPreferences:
                    intent.setClass(MainActivity.this, SharedPreferencesActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };
}