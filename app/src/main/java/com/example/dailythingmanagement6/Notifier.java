package com.example.dailythingmanagement6;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Notifier extends BroadcastReceiver {

    @Override
    public void onReceive(Context content, Intent intent) {

        //通知がクリックされた時に発行されるIntentの生成
        Intent sendIntent = new Intent(content, MainActivity.class);
        PendingIntent sender = PendingIntent.getActivity(content, 0, sendIntent, 0);

        //通知オブジェクトの生成
        Notification noti = new NotificationCompat.Builder(content)
                .setTicker("お時間ですよ!")
                .setContentTitle("通知")
                .setContentText("設定した時間がきました")
                .setSmallIcon(R.drawable.milk)
                .setVibrate(new long[]{0, 200, 100, 200, 100, 200})
                .setAutoCancel(true)
                .setContentIntent(sender)
                .build();

        NotificationManager manager = (NotificationManager)content.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, noti);
    }
}