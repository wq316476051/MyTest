package com.wang.mytest.feature.storage

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

fun hasStoragePermission(context: Context) =
        context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

fun requestStoragePermission(activity: Activity, code: Int) =
        activity.requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), code)
