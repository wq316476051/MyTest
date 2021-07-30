package com.wang.mytest.common.util;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtils {

    private ToastUtils() {
    }

    public static void showShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
