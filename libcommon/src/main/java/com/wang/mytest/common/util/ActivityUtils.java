package com.wang.mytest.common.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public final class ActivityUtils {

    private static final String TAG = "ActivityUtils";

    private ActivityUtils() {
    }
    public static void startActivity(@Nullable Context context, @Nullable Intent intent) {
        startActivity(context, intent, null);
    }


    public static void startActivity(@Nullable Context context, @Nullable Intent intent, @Nullable ActivityOptions options) {
        if (context == null || intent == null) {
            return;
        }
        if ((context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            if (options != null) {
                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            LogUtils.error(TAG, "startActivity: failed");
        }
    }
}
