package com.rovaindu.homeservice.utils;

import android.app.Application;
import android.content.Context;

import com.rovaindu.homeservice.R;


/**
 * Created by Mohamed El Sayed
 */
public class GetTimeAgo extends Application {



    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return ctx.getString(R.string.just_now);
        } else if (diff < 2 * MINUTE_MILLIS) {
            return ctx.getString(R.string.min_ago);
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " " + ctx.getString(R.string.minites_ago);
        } else if (diff < 90 * MINUTE_MILLIS) {
            return ctx.getString(R.string.hour_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " " + ctx.getString(R.string.hours_ago);
        } else if (diff < 48 * HOUR_MILLIS) {
            return ctx.getString(R.string.yesterday);
        } else {
            return diff / DAY_MILLIS + " " + ctx.getString(R.string.days_ago);
        }
    }

}