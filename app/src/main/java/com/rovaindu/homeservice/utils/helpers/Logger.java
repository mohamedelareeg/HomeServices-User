package com.rovaindu.homeservice.utils.helpers;


import android.util.Log;


public class Logger {
    private static final String LOGGER_TAG = "RovaIndu";
    private static boolean isDev = false;
    private static boolean enableLogs = false;

    public Logger() {
    }

    public static boolean isDev() {
        return isDev;
    }

    public static boolean isLogsEnabled() {
        return enableLogs;
    }

    public static void error(String message) {
        if (isLogsEnabled()) {
            Log.e("RovaIndu", message);
        }

    }

    public static void switchToDev(String password) {
        String finalHash = chatUtils.getMd5(password + "I<RovaIndu");
        if (finalHash.equalsIgnoreCase("26B551C56986EC476142F6F74FB2BE6A")) {
            isDev = true;
        }

    }

    public static void enableLogs(String password) {
        String finalHash = chatUtils.getMd5(password + "I<RovaIndu");
        if (finalHash.equalsIgnoreCase("26B551C56986EC476142F6F74FB2BE6A")) {
            enableLogs = true;
        }

    }

    public static void debug(String message) {
        Log.d("RovaIndu", message);
    }

    public static void info(String message) {
        Log.i("RovaIndu", message);
    }

    public static void error(String TAG, String message) {
        if (enableLogs) {
            Log.e(TAG, message);
        }

    }

    public static void exception(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static void debug(String TAG, String message) {
        Log.d(TAG, message);
    }

    public static void info(String TAG, String message) {
        Log.i(TAG, message);
    }

    public static void errorLong(String TAG, String message) {
        int maxLogSize = 1000;

        for(int i = 0; i <= message.length() / maxLogSize; ++i) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            error(TAG, message.substring(start, end));
        }

    }
}
