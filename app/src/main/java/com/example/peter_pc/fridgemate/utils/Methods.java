package com.example.peter_pc.fridgemate.utils;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by peter-pc on 3/14/2018.
 */

public class Methods {

    //Computes days remaining for product to expire
    public long getRemainingDays(String expiryTime) {
        long expiry_date = 0;
        if (expiryTime == null) {
            expiry_date = 0;
        } else {
            expiry_date = Long.valueOf(expiryTime);
        }
        Date todayDate = Calendar.getInstance().getTime();
        long diff = expiry_date - todayDate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = (hours / 24) + 1;
        return days;
    }

    public int getMonthNumber(String month) {
        int monthNumber = 0;
        String compare1 = month.substring(0,3);
        Log.e("getMonthNumber: ", compare1 );
        if (compare1.equalsIgnoreCase("Jan")) {
            monthNumber = 1;
        } else if (compare1.equalsIgnoreCase("Feb")) {
            monthNumber = 2;
        } else if (compare1.equalsIgnoreCase("Mar")) {
            monthNumber = 3;
        } else if (compare1.equalsIgnoreCase("Apr")) {
            monthNumber = 4;
        } else if (compare1.equalsIgnoreCase("May")) {
            monthNumber = 5;
        } else if (compare1.equalsIgnoreCase("Jun")) {
            monthNumber = 6;
        } else if (compare1.equalsIgnoreCase("Jul")) {
            monthNumber = 7;
        } else if (compare1.equalsIgnoreCase("Aug")) {
            monthNumber = 8;
        } else if (compare1.equalsIgnoreCase("Sep")) {
            monthNumber = 9;
        } else if (compare1.equalsIgnoreCase("Oct")) {
            monthNumber = 10;
        } else if (compare1.equalsIgnoreCase("Nov")) {
            monthNumber = 11;
        } else if (compare1.equalsIgnoreCase("Dec")) {
            monthNumber = 12;
        }

        return monthNumber;
    }
}
