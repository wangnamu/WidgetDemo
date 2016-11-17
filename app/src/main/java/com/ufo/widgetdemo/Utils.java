package com.ufo.widgetdemo;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by tjpld on 2016/10/10.
 */

public class Utils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int hegiht = dm.heightPixels;
        return hegiht;
    }

    public static String date2string(Date date) {
        long different = new Date().getTime() - date.getTime();

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

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    //复制
    public static void copyText(String text, Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData textCd = ClipData.newPlainText("text", text);
        clipboard.setPrimaryClip(textCd);
    }

    //震动
    public static void vibrator(Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);

        long[] pattern = {0, 1};
        vib.vibrate(pattern, -1);
    }
}
