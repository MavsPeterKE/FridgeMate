package com.example.peter_pc.fridgemate.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.peter_pc.fridgemate.ui.MainActivity;

import java.util.Calendar;

public class NotifyAlarmManager {
    public static final int REQUEST_CODE = 111131;
    //private final static long INTERVAL=30*1000;
    //private final static long INTERVAL=7200000;
    public static final int FIVE_MINUTE = 300000;

    public static void setAlaram(Context context) {

        Calendar cal = Calendar.getInstance();

        //cal.add(Calendar.HOUR, 5);
        cal.add(Calendar.MINUTE, 5);

        long tomorrow = cal.getTimeInMillis();

        Intent intent = new Intent(context, MainActivity.class);

        boolean alarmRunning = (PendingIntent.getBroadcast(context, REQUEST_CODE,intent,
                PendingIntent.FLAG_NO_CREATE) != null);

        //Check if alarm is already running
        if(alarmRunning){
            Log.e("AlaramManager", "Alaram already running!");
            return;
        }
        PendingIntent sender = PendingIntent.getBroadcast(context,REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,tomorrow,FIVE_MINUTE, sender);
        Log.e("alaram", "alaram set");
        //am.setRepeating(AlarmManager.RTC_WAKEUP,tomorrow,AlarmManager.INTERVAL_FIFTEEN_MINUTES, sender);

    }

    public static void updateAlaram(Context context) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 5);

        long tomorrow = cal.getTimeInMillis();

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent sender = PendingIntent.getBroadcast(context,REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        am.setRepeating(AlarmManager.RTC_WAKEUP,tomorrow,FIVE_MINUTE, sender);

    }

    public static void cancelAlaram(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}
