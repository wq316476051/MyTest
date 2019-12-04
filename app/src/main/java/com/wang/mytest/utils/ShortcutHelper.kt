package com.wang.mytest.utils

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import com.wang.mytest.R

@TargetApi(Build.VERSION_CODES.N_MR1)
class ShortcutHelper(context: Context) {

    companion object {
        fun create(app: Application) = ShortcutHelper(app)
    }

    private var mContext: Context = context
    private var mShortcutManager: ShortcutManager
            = context.getSystemService(ShortcutManager::class.java)

    init {
        if (isValid()) {
            val info = ShortcutInfo.Builder(mContext, "dynamic")
                    .setIcon(Icon.createWithResource(mContext.packageName, R.drawable.ic_launcher_background))
                    .setShortLabel("Short Label")
                    .setLongLabel("Long Label")
                    .setIntent(Intent().apply {
                    })
                    .build()
            mShortcutManager.dynamicShortcuts = arrayListOf(info)
        }
    }

    private fun isValid() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
}