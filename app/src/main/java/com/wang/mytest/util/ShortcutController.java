package com.wang.mytest.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import com.wang.mytest.R;

import java.util.Collections;

@TargetApi(Build.VERSION_CODES.N_MR1)
public class ShortcutController {

    private Context mContext;
    private ShortcutManager mShortcutManager;

    public ShortcutController(Context context) {
        mContext = context.getApplicationContext();
        mShortcutManager = context.getSystemService(ShortcutManager.class);
    }

    public void add() {
        ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(mContext, "dynamic")
                .setIcon(Icon.createWithResource(mContext.getPackageName(), R.drawable.ic_launcher_background))
                .setShortLabel("Short Label")
                .setLongLabel("Long Label")
                .setIntent(new Intent())
                .build();
        mShortcutManager.addDynamicShortcuts(Collections.singletonList(shortcutInfo));
    }

    private boolean isValid() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }
}
