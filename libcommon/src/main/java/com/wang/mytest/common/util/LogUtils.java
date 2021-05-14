package com.wang.mytest.common.util;

import android.util.Log;

public final class LogUtils {
    private static final String PREFIX = "[MyTest]";

    private LogUtils() {
    }

    public static void d(String tag, String msg) {
        d(PREFIX, tag, msg);
    }

    public static void d(String prefix, String tag, String msg) {
        Log.d(prefix + tag, msg);
    }
}
