package com.sample.ui.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sample.ui.R;
import com.sample.ui.data.SQLiteActivity;

import java.util.Map;
import java.util.Random;

public class FcmService extends FirebaseMessagingService {
    public static final String KEY_FCM_TOKEN = "fcmToken";

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map data = message.getData();
        Notify(data);
    }

    @Override
    public void onNewToken(@NonNull String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.i("FcmService", "refreshedToken=" + refreshedToken);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sp.edit().putString(KEY_FCM_TOKEN, refreshedToken).commit();
        getApplicationContext();
    }

    private void Notify(Map data) {
        String title = data.get("title") == null ? "" : data.get("title").toString();
        String msg = data.get("message") == null ? "" : data.get("message").toString();

        int AppLargeIcon = R.mipmap.ic_launcher;
        int AppSmallIcon = R.mipmap.ic_launcher;

        int notificationID = Math.abs(new Random().nextInt());
        Intent notifyIntent = new Intent(this, SQLiteActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, notificationID, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
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
                .setContentTitle(title)
                .setContentText(msg)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setColor(Color.parseColor("#882F70"));

        nManager.notify(notificationID, builder.build());

    }

}