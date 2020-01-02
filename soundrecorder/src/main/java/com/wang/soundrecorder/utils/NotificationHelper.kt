package com.wang.soundrecorder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.wang.soundrecorder.App

object NotificationHelper {

    private const val CHANNEL_ID = "id"
    private const val CHANNEL_NAME = "name"

    fun prepareChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            App.instance.getSystemService(NotificationManager::class.java)?.apply {
                createNotificationChannel(NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT))
            }
        }
    }

    fun notifyRecording() {

    }

    fun notifyPlaying() {

    }
}