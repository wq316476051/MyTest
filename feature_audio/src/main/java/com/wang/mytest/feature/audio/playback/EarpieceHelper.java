package com.wang.mytest.feature.audio.playback;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class EarpieceHelper implements DefaultLifecycleObserver {

    private static final String TAG = "EarpieceHelper";

    private AtomicBoolean mSupported = new AtomicBoolean(false);
    private AtomicBoolean mRegistered = new AtomicBoolean(false);

    private MutableLiveData<Boolean> mEarpiece = new MutableLiveData<>();

    private AudioManager mAudioManager;
    private PowerManager mPowerManager;
    private SensorManager mSensorManager;
    private Sensor mProximitySensor;
    private PowerManager.WakeLock mWakeLock;

    private EarpieceHelper(Context context) {
        mAudioManager = ContextCompat.getSystemService(context, AudioManager.class);
        mPowerManager = ContextCompat.getSystemService(context, PowerManager.class);
        mSensorManager = ContextCompat.getSystemService(context, SensorManager.class);
        if (mSensorManager != null) {
            mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
        if (mAudioManager != null || mPowerManager != null && mProximitySensor != null) {
            mSupported.set(true);
        }
    }

    public static EarpieceHelper create(@NonNull Context context) {
        return new EarpieceHelper(context);
    }

    public LiveData<Boolean> getEarpiece() {
        return Transformations.distinctUntilChanged(mEarpiece);
    }

    public void startWatching() {
        if (mSupported.get() && mRegistered.compareAndSet(false, true)) {
            mSensorManager.registerListener(mSensorEventListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stopWatching() {
        if (mSupported.get() && mRegistered.compareAndSet(true, false)) {
            mSensorManager.unregisterListener(mSensorEventListener);
        }
    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (isHeadphonesPlugged()) {
                Log.d(TAG, "onSensorChanged: headset plugged.");
                return;
            }
            int type = event.sensor.getType();
            if (type == Sensor.TYPE_PROXIMITY) {
                float distance = event.values[0];
                Log.d(TAG, "onSensorChanged: distance = " + distance);
                Log.d(TAG, "onSensorChanged: maxRange = " + event.sensor.getMaximumRange());
                if (distance >= event.sensor.getMaximumRange()) {
                    changeToSpeaker();
                    screenOn();
                } else {
                    changeToEarpiece();
                    screenOff();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void changeToSpeaker() {
        if (mSupported.get()) {
            Log.d(TAG, "changeToSpeaker: changed to speaker");
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mAudioManager.setSpeakerphoneOn(true);
            mEarpiece.postValue(false);
        }
    }

    private void changeToEarpiece() {
        if (mSupported.get()) {
            Log.d(TAG, "changeToEarpiece: changed to earpiece");
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            mAudioManager.setSpeakerphoneOn(false);
            mEarpiece.postValue(true);
        }
    }

    private void screenOn() {
        if (mSupported.get()) {
            if (mWakeLock == null) {
                mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "recording::earpiece");
            }
            if (!mWakeLock.isHeld()) {
                mWakeLock.acquire(10 * 60 * 1000);
            }
        }
    }

    private void screenOff() {
        if (mSupported.get()) {
            if (mWakeLock != null && mWakeLock.isHeld()) {
                mWakeLock.setReferenceCounted(false); // 设置不启用引用计数
                mWakeLock.release();
            }
        }
    }

    private boolean isHeadphonesPlugged() {
        if (mSupported.get()) {
            AudioDeviceInfo[] audioDevices = mAudioManager.getDevices(AudioManager.GET_DEVICES_ALL);
            for (AudioDeviceInfo deviceInfo : audioDevices) {
                if (deviceInfo.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES
                        || deviceInfo.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET
                        || deviceInfo.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
                        || deviceInfo.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        stopWatching();
    }
}
