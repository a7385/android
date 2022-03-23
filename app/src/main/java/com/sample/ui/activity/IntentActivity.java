package com.sample.ui.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

public class IntentActivity extends AppCompatActivity {

    public static final int INTENT_SUCCESS = 1;
    public static final int INTENT_FAIL = 2;

    public static int count = 0;

    private TextView m_TextView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        count++;

        Button activity = findViewById(R.id.activity);
        activity.setOnClickListener(m_onClickListener);

        Button url = findViewById(R.id.url);
        url.setOnClickListener(m_onClickListener);

        Button youtube = findViewById(R.id.youtube);
        youtube.setOnClickListener(m_onClickListener);

        Button share = findViewById(R.id.share);
        share.setOnClickListener(m_onClickListener);

        Button deeplink = findViewById(R.id.deeplink);
        deeplink.setOnClickListener(m_onClickListener);

        m_TextView_result = findViewById(R.id.result);

    }

    View.OnClickListener m_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.activity:
                    mStartForResult.launch(new Intent(IntentActivity.this, ResultActivity.class));
                    break;
                case R.id.url:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cwb.gov.tw/V8/C/E/index.html"));
                    startActivity(intent);
                    break;
                case R.id.youtube:

                    String url = "https://www.youtube.com/user/google";
                    try {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        //https://play.google.com/store/apps/details?id=com.google.android.youtube
                        intent.setPackage("com.google.android.youtube");
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                    break;
                case R.id.share:
                    try {
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                        intent.putExtra(Intent.EXTRA_TEXT, "分享文字內容");
                        startActivity(Intent.createChooser(intent, "請選擇"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.deeplink:
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("test://?url=https://1922.gov.tw"));
                    startActivity(intent);
                    break;
            }
        }
    };

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == INTENT_SUCCESS) {
                        m_TextView_result.setText("result ： INTENT_SUCCESS");
                        new AlertDialog.Builder(IntentActivity.this)
                                .setTitle("提醒")
                                .setMessage("成功！！！！")
                                .setNegativeButton(android.R.string.ok, null)
                                .create()
                                .show();

                    } else if (result.getResultCode() == INTENT_FAIL) {
                        m_TextView_result.setText("result ： INTENT_FAIL");
                        new AlertDialog.Builder(IntentActivity.this)
                                .setTitle("提醒")
                                .setMessage("失敗！！！！")
                                .setNegativeButton(android.R.string.ok, null)
                                .create()
                                .show();
                    }
                }
            });
}
