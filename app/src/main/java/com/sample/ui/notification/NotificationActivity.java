package com.sample.ui.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.sample.ui.R;

public class NotificationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0x11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        EditText notificationMessage = findViewById(R.id.notificationMessage);
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                sendNotification("" + notificationMessage.getText());
            }
        });

    }

    private void sendNotification(String message) {

        NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= 26) {
            //手機上設定 = > 通知 => 可找到 目前的app 所使用的所有 Channel
            String id = "SAMPLE";
            NotificationChannel mChannel = new NotificationChannel(id, "SAMPLE", NotificationManager.IMPORTANCE_LOW);
            nManager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(getApplicationContext(), id);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("sample")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setColor(Color.parseColor("#882F70"));

        nManager.notify(REQUEST_CODE, builder.build());
    }
}