package com.wang.mytest.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

const val STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
const val AUDIO_PERMISSION = android.Manifest.permission.RECORD_AUDIO

const val REQUEST_CODE_STORAGE = 1024
const val REQUEST_CODE_AUDIO = 1025
const val REQUEST_CODE_STORAGE_AND_AUDIO = 1026

fun Context.hasPermission(permission: String): Boolean {
    return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasStoragePermission(): Boolean = hasPermission(STORAGE_PERMISSION)
fun Context.hasAudioPermission(): Boolean = hasPermission(AUDIO_PERMISSION)

fun Activity.requestStoragePermission() =
        requestPermissions(arrayOf(STORAGE_PERMISSION), REQUEST_CODE_STORAGE)

fun Activity.shouldShowStorageRationale() =
        shouldShowRequestPermissionRationale(STORAGE_PERMISSION)

fun Activity.shouldShowAudioRationale() =
        shouldShowRequestPermissionRationale(AUDIO_PERMISSION)
