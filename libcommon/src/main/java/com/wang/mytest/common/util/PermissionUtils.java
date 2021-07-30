package com.wang.mytest.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public final class PermissionUtils {

    private static final int REQUEST_CODE_STORAGE = 1024;

    private PermissionUtils() {
    }

    public static boolean hasStoragePermission(Context context) {
        return hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean hasPermission(Context context, String permission) {
        if (context == null || TextUtils.isEmpty(permission)) {
            return false;
        }
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestStoragePermission(Activity activity) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        activity.requestPermissions(permissions, REQUEST_CODE_STORAGE);
    }

}
