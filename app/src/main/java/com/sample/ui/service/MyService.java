package com.sample.ui.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.sample.ui.R;
import com.sample.ui.data.SQLiteActivity;

public class MyService extends Service {

    private static final int REQUEST_CODE = 0x111111;
    private static int count = 0;

    private Handler m_handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
//        m_handler.postDelayed(run, 1000);
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showNotification() {
        count++;
        CharSequence text = "程式運作中，點擊此處進入APP " + count;

        int AppLargeIcon = R.mipmap.ic_launcher;
        int AppSmallIcon = R.mipmap.ic_launcher;

        Intent notifyIntent = new Intent(this, SQLiteActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, REQUEST_CODE, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
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

        builder.setSmallIcon(AppSmallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), AppLargeIcon))
                .setContentTitle("sample")
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setColor(Color.parseColor("#882F70"));

//        nManager.notify(REQUEST_CODE, builder.build());

        //常駐通知
        startForeground(0x2014, builder.build());
    }

    private Runnable run = new Runnable() {
        public void run() {
            showNotification();
            m_handler.postDelayed(this, 5000);
        }
    };
}