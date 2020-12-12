package com.example.dailythingmanagement6;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class Notifier extends BroadcastReceiver {
    private String name = "";
    private Integer picname = null;


    @Override
    public void onReceive(Context content, Intent intent) {
        //通知がクリックされた時に発行されるIntentの生成
        Intent sendIntent = new Intent(content, MainActivity.class);
        PendingIntent sender = PendingIntent.getActivity(content, 0, sendIntent, 0);
        name = intent.getStringExtra("name");
        picname = intent.getIntExtra("picname",1);


        //通知オブジェクトの生成
        Notification noti = new NotificationCompat.Builder(content)
                .setTicker("お時間ですよ!")
                .setContentTitle(name)
                .setContentText(name+"が残り少ないです")
                .setSmallIcon(picname)
                .setVibrate(new long[]{0, 200, 100, 200, 100, 200})
                .setAutoCancel(true)
                .setContentIntent(sender)
                .build();

        NotificationManager manager = (NotificationManager)content.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(intent.getIntExtra("id",0), noti);
    }
}