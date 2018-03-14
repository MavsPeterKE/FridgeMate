package com.example.peter_pc.fridgemate.utils;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by peter-pc on 3/14/2018.
 */

public class Methods {

    //Computes days remaining for product to expire
    public long getRemainingDays(String expiryTime){
        long expirydate= Long.valueOf(expiryTime);
        Date todayDate= Calendar.getInstance().getTime();
        long diff = expirydate-todayDate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = (hours / 24) + 1;
        Log.e( "onDateSet: ",""+expirydate );
        Log.e( "onDateSet: ",""+todayDate.getTime() );
        return days;
    }
}
