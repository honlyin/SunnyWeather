package com.example.sunnyweather.utils;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "SunnyWeather_";

    public static void v(String message) {
        Log.v(TAG, message);
    }

    public static void v(String tag, String message) {
        Log.v(TAG + tag, message);
    }

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void i(String tag, String message) {
        Log.i(TAG + tag, message);
    }

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void d(String tag, String message) {
        Log.d(TAG + tag, message);
    }

    public static void w(String message) {
        Log.w(TAG, message);
    }

    public static void w(String tag, String message) {
        Log.w(TAG + tag, message);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void e(String tag, String message) {
        Log.e(TAG + tag, message);
    }
}
