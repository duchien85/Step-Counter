package com.pedja1.stepcounter.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

/**
 * Created by pedja on 30.7.14. 11.49.
 * This class is part of the StepCounter
 * Copyright © 2014 ${OWNER}
 */
public class NotificationUtils
{

    // Put the message into a notification and post it.
    public static void sendNotification(int stepCount, Context context)
    {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        Intent dataIntent = new Intent(context, MainActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(dataIntent);

        PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder mBuilder = new Notification.Builder(context);

        //users avatar
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));

        //maybe type icon(message icon for example if its message)
        mBuilder.setSmallIcon(R.drawable.ic_stat_notif);

        mBuilder.setContentTitle(context.getString(R.string.app_name));

        mBuilder.setContentText("Steps Taken: " + stepCount);
        //mBuilder.setAutoCancel(true);
        //mBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.FLAG_ONLY_ALERT_ONCE);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(-273, mBuilder.build());
    }

    public static void clearNotification(Context context)
    {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(-273);
    }
}
