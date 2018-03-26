package com.example.peter_pc.fridgemate.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.ui.RecyclerListActivity;

import java.util.ArrayList;

/**
 * Created by peter on 3/13/2018.
 */

public class NotificationUtils {
    Context context;
    private static final String CHANNEL_ID = "Ex01";

    public NotificationUtils(Context context) {
        this.context = context;
    }

    //Triggers user notification
    public void notifyUser(String title, int expiry_count, String content) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.badge_icon)
                .setContentTitle(expiry_count+" "+title)
                .setContentText(content)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //gets default notification sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        Intent resultIntent = new Intent(context, RecyclerListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(RecyclerListActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.build();

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());

    }

    //creates inbox notification model
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void inboxStyleNotification(ArrayList<String> products_expiring, int productNumbers, int remProducts ){
        String addLine1=products_expiring.get(0);
       // String addLine2=products_expiring.get(1);
        //String addLine3=products_expiring.get(2);
        /*String addLine4=products_expiring.get(3);
        String addLine5=products_expiring.get(4);*/

        Intent resultIntent = new Intent(context, RecyclerListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(RecyclerListActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder=new Notification.Builder(context)
                .setSmallIcon(R.drawable.expires)
                .setColor(Color.RED)
                .setContentTitle(productNumbers+" "+Constants.EXPIRY_NOTICE)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentText(Constants.EXPIRY_TEXT_)
                //.setColor(Color.RED)
                .setContentIntent(resultPendingIntent);

        //gets default notification sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        Notification notification = new Notification.InboxStyle(builder)
                .addLine(addLine1)
                //.addLine(addLine2)
                //.addLine(addLine3)
                /*.addLine(addLine4)
                .addLine(addLine5)*/
                .setBigContentTitle(Constants.EXPIRY_INBOX_ALERT)
                .setSummaryText(remProducts+ " more")
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
    }
}
