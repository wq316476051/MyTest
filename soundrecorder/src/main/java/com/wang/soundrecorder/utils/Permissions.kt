package com.wang.soundrecorder.utils

import android.app.Activity
import android.content.pm.PackageManager
import com.wang.soundrecorder.App

object Permissions {

    private const val RECORD_AUDIO = android.Manifest.permission.RECORD_AUDIO
    private const val WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    const val REQUEST_CODE_RECORD_AUDIO = 1000
    const val REQUEST_CODE_STORAGE = 1001
    const val REQUEST_CODE_RECORD = REQUEST_CODE_RECORD_AUDIO + REQUEST_CODE_STORAGE

    fun hasRecordAudioPermission(): Boolean = App.instance.checkSelfPermission(
            RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    fun hasStoragePermission(): Boolean = App.instance.checkSelfPermission(
            WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    fun requestRecordPermissions(activity: Activity): Boolean {
        val hasRecordAudioPermission = hasRecordAudioPermission()
        val hasStoragePermission = hasStoragePermission()
        if (hasRecordAudioPermission && hasStoragePermission) {
            return true
        }
        activity.requestPermissions(arrayOf(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE), REQUEST_CODE_RECORD)
        return false
    }
}