package com.wang.mytest.feature.audio

import android.media.MediaExtractor
import android.media.MediaFormat
import android.util.Log
import com.wang.mytest.feature.audio.bean.Recording
import java.lang.Exception

fun Recording.calcDuration(): Long {
    try {
        val extractor = MediaExtractor().apply {
            setDataSource(this@calcDuration.filepath)
        }
        for (trackId in 0 until extractor.trackCount) {
            extractor.getTrackFormat(trackId).apply {
                if (getString(MediaFormat.KEY_MIME)?.startsWith("audio") == true) {
                    return@calcDuration getLong(MediaFormat.KEY_DURATION) / 1000
                }
            }
        }
    } catch (e: Exception) {
        Log.e("Recording", "calcDuration: " + e.message)
    }
    return -1
}