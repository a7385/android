package com.sample.ui.thread;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpActivity extends AppCompatActivity {

    private TextView m_TextView_response;
    private ProgressDialog m_waitDialog;

    private Handler m_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        m_handler = new Handler();
        m_waitDialog = new ProgressDialog(OkhttpActivity.this);
        m_waitDialog.setMessage("處理中");
        Button get = findViewById(R.id.get);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_waitDialog.show();
                sendGET();
            }
        });

        Button post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_waitDialog.show();
                sendPOST();
            }
        });
        m_TextView_response = findViewById(R.id.response);

    }

    private void sendGET() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
//                .header("Cookie","")//有Cookie需求的話則可用此發送
//                .addHeader("","")//如果API有需要header的則可使用此發送
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                m_waitDialog.dismiss();

                m_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            m_TextView_response.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

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
        });
    }

    private void sendPOST() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        FormBody formBody = new FormBody.Builder()
                .add("id", "1")
                .add("title", "post")
                .add("message", "hello")
                .build();

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .post(formBody)
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