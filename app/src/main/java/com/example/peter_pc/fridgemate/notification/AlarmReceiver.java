package com.example.peter_pc.fridgemate.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.ui.MainActivity;
import com.example.peter_pc.fridgemate.ui.RecyclerListActivity;
import com.example.peter_pc.fridgemate.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ptyagi on 4/17/17.
 */

/**
 * AlarmReceiver handles the broadcast message and generates Notification
 */
public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        //Get notification manager to manage/send notifications


        //Intent to invoke app when click on notification.
        //In this sample, we want to start/launch this sample app when user clicks on notification
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        //set flag to restart/relaunch the app
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build notification
        ArrayList<String>products= new ArrayList<>();
        products.add("Fish");
        products.add("Eggs");
        products.add("Mangoe");
        Notification repeatedNotification = buildLocalNotification(context, pendingIntent,products,3,1).build();

        //Send local notification
        NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Notification.Builder buildLocalNotification(Context context, PendingIntent pendingIntent,ArrayList<String> products_expiring, int productNumbers, int remProducts ) {
        String addLine1=products_expiring.get(0);
        Notification.Builder builder=new Notification.Builder(context)
                .setSmallIcon(R.drawable.expires)
                .setColor(Color.RED)
                .setContentTitle(productNumbers+" "+ Constants.EXPIRY_NOTICE)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentText(Constants.EXPIRY_TEXT_)
                //.setColor(Color.RED)
                .setContentIntent(pendingIntent);
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

        return builder;
    }
}
