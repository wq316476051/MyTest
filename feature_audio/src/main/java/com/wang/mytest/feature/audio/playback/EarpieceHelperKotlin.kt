package com.wang.mytest.feature.audio.playback

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.os.PowerManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.util.concurrent.atomic.AtomicBoolean

class EarpieceHelperKotlin(val context: Context) : DefaultLifecycleObserver {

    private val supported by lazy { AtomicBoolean(false) }
    private val registered by lazy { AtomicBoolean(false) }
    val earpiece: LiveData<Boolean> = MutableLiveData<Boolean>()
        get() = Transformations.distinctUntilChanged(field)

    private var audioManager = context.getSystemService(AudioManager::class.java)
    private var powerManager = context.getSystemService(PowerManager::class.java)
    private var sensorManager = context.getSystemService(SensorManager::class.java)

    private var proximitySensor: Sensor? = null
    private var wakeLock: PowerManager.WakeLock? = null

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    init {
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    fun startWatching() {
        proximitySensor?.apply {
            sensorManager.registerListener(null, this, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
}