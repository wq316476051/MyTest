package com.wang.mytest.common.util;

import android.util.Log;

public final class LogUtils {
    private static final String PREFIX = "[MyTest]";

    private LogUtils() {
    }

    public static void debug(String tag, String msg) {
        Log.d(PREFIX + tag, msg);
    }

    public static void info(String tag, String msg) {
        Log.i(PREFIX + tag, msg);
    }

    public static void warn(String tag, String msg) {
        Log.w(PREFIX + tag, msg);
    }

    public static void error(String tag, String msg) {
        Log.e(PREFIX + tag, msg);
    }
}
