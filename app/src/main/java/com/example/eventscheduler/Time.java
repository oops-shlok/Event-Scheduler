package com.example.eventscheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Time extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("Notificationid",0);
        String message = intent.getStringExtra("event");
        Intent main = new Intent(context,MainActivity.class);
        PendingIntent content = PendingIntent.getActivity(context,0,main,0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.bell).setContentTitle("Do this!").setContentText(message).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(content).setPriority(Notification.PRIORITY_MAX).setDefaults(Notification.DEFAULT_ALL);

        notificationManager.notify(notificationId,builder.build());
    }
}
