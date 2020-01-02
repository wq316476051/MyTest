package com.wang.soundrecorder.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PlaybackService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}