package com.sample.ui.activity;

import static com.sample.ui.activity.IntentActivity.INTENT_FAIL;
import static com.sample.ui.activity.IntentActivity.INTENT_SUCCESS;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button susses = findViewById(R.id.susses);
        susses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(INTENT_SUCCESS);
                finish();
            }
        });

        Button fail = findViewById(R.id.fail);
        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(INTENT_FAIL);
                finish();
            }
        });

    }
}
