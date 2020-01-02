package com.wang.soundrecorder.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.wang.soundrecorder.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timerTask

/**
 * 1、录音
 * 2、列表 & 播放
 * 3、跳过静音
 * 4、贴近听筒，切换为听筒
 * 5、降采样
 * 6、speech to text
 * 7、TAG
 */
class RecordingsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RecordingsActivity"
    }

    private lateinit var btnStartNewRecord: Button

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordings)

        btnStartNewRecord = findViewById(R.id.btn_start_new_record)

        btnStartNewRecord.setOnClickListener {
            startActivity(Intent(this, SoundRecorderActivity::class.java))
        }

        btnStartNewRecord.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            override fun sendAccessibilityEventUnchecked(host: View?, event: AccessibilityEvent?) {
                Log.d(TAG, "sendAccessibilityEventUnchecked: eventType = ${event?.eventType}")
                if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                    return
                }
                if (event?.eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    btnStartNewRecord.contentDescription = "Test description ${System.currentTimeMillis()}"
                }
                super.sendAccessibilityEventUnchecked(host, event)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}