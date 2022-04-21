package com.sample.ui.google;

import static com.sample.ui.service.FcmService.KEY_FCM_TOKEN;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class FcmActivity extends AppCompatActivity {
    private static final String API_KEY = "SERVER API KEY";

    private EditText m_EditText_title;
    private EditText m_EditText_message;
    private TextView m_TextView_response;
    private TextView m_TextView_token;
    private ProgressDialog m_waitDialog;

    private String m_FCMToken;

    private Handler m_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);
        m_FCMToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(KEY_FCM_TOKEN, "");
        m_handler = new Handler();
        m_waitDialog = new ProgressDialog(FcmActivity.this);
        m_waitDialog.setMessage("處理中");

        m_EditText_title = findViewById(R.id.sendTitle);
        m_EditText_message = findViewById(R.id.sendMessage);

        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_waitDialog.show();
                String title = "" + m_EditText_title.getText();
                String message = "" + m_EditText_message.getText();
                sendFCM(title, message);
            }
        });
        m_TextView_response = findViewById(R.id.response);
        m_TextView_token = findViewById(R.id.token);
        m_TextView_token.setText(m_FCMToken);

    }

    private void sendFCM(String title, String message) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();


        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("to", m_FCMToken);
            data.put("title", title);
            data.put("message", message);
            body.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(body.toString(), MediaType.parse("application/json;charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "key=" + API_KEY)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                m_waitDialog.dismiss();

//                如果傳送過程有發生錯誤
                m_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        m_TextView_response.setText(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                m_waitDialog.dismiss();

                m_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            m_TextView_response.setText("POST回傳：\n" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}