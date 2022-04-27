package com.example.grow;

import static android.app.Notification.*;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    void showNotification(Context context) {
        String CHANNEL_ID = "your_name";
        CharSequence name = context.getResources().getString(R.string.app_name);
        NotificationCompat.Builder mBuilder;
        Intent notificationIntent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        notificationIntent.putExtras(bundle);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(mChannel);
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_icon)
                    .setLights(Color.RED, 300, 300)
                    .setChannelId(CHANNEL_ID)
                    .setContentTitle("Grow!");
        } else {
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_icon)
                    .setPriority(PRIORITY_MAX)
                    .setContentTitle("Grow!");
        }

        Random random = new Random();
        String message;
        switch (random.nextInt(3)){
            case 0:
                message = "Don't forget about your habits!";
                break;
            case 1:
                message = "Did you grow your flowers?";
                break;
            case 2:
                message = "Your flowers are waiting for you!";
                break;
            default:
                message = "What about your habits?";
                break;
        }

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setContentText(message);
        mBuilder.setAutoCancel(true);

        Notification notification = mBuilder.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mNotificationManager.notify(1, notification);
    }
}
