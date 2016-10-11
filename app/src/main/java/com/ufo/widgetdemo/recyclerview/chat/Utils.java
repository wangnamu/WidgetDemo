package com.ufo.widgetdemo.recyclerview.chat;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tjpld on 2016/10/10.
 */

public class Utils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    public static String date2string(Date date) {
        long different =  new Date().getTime()-date.getTime() ;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        if (elapsedDays == 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
            return dateFormat.format(date);
        } else if (elapsedDays > 0 && elapsedDays <= 1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
            return String.format("昨天 %s", dateFormat.format(date));
        } else if (elapsedDays > 1 && elapsedDays <= 7) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("E a hh:mm");
            return dateFormat.format(date);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 a hh:mm");
            return dateFormat.format(date);
        }

    }

    public static boolean in15Min(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;


        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        if (elapsedDays > 0) {
            return true;
        }

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        if (elapsedHours > 0) {
            return true;
        }

        long elapsedMinutes = different / minutesInMilli;
        //different = different % minutesInMilli;

        if (elapsedMinutes >= 15) {
            return true;
        }

        return false;
    }

    public static Date string2date(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }
}
