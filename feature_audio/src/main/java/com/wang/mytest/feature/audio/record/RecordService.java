package com.wang.mytest.feature.audio.record;

import android.app.Service;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wang.mytest.feature.audio.RecordStorage;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import androidx.annotation.Nullable;

public class RecordService extends Service {

    private static final String TAG = "RecordService";

    private BinderService mBinderService;

    private WeakReference<IRecordCallback> mRecordCallback;

    private MediaRecorder mRecorder;

    private int mState = RecordManager.STATE_IDLE;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        mBinderService = new BinderService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderService;
    }

    private void registerCallbackInternal(IRecordCallback callback) throws RemoteException {
        mRecordCallback = new WeakReference<>(callback);
        callback.onStateChanged(mState);
    }

    private void unregisterCallbackInternal() {
        mRecordCallback.clear();
    }

    private void startRecordingInternal() {
        File record = new File(RecordStorage.getStorageDirectory(RecordService.this),
                new SimpleDateFormat("yyyyMMdd_HH_mm_ss").format(new Date()) + ".m4a");
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFile(record.getAbsolutePath());
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(2);
        mRecorder.setAudioSamplingRate(4800);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        wrapException(() -> {
            mRecorder.start();
            setStateAndNotify(RecordManager.STATE_RECORDING);
        });
    }

    private void pauseInternal() {
        wrapException(() -> {
            mRecorder.pause();
            setStateAndNotify(RecordManager.STATE_PAUSED);
        });
    }

    private void resumeInternal() {
        wrapException(() -> {
            mRecorder.resume();
            setStateAndNotify(RecordManager.STATE_RECORDING);
        });
    }

    private void stopRecordingInternal() {
        wrapException(() -> {
            mRecorder.stop();
            mRecorder.release();
            setStateAndNotify(RecordManager.STATE_IDLE);
        });
    }

    private int getAmplitudeInternal() {
        return wrapException(() -> {
            return mRecorder.getMaxAmplitude();
        }, -1);
    }

    private int getStateInternal() {
        return mState;
    }

    private void wrapException(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
            mRecorder.release();
            setStateAndNotify(RecordManager.STATE_IDLE);
        }
    }

    private int wrapException(Callable<Integer> callable, int defValue) {
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            mRecorder.release();
            setStateAndNotify(RecordManager.STATE_IDLE);
        }
        return defValue;
    }

    private void setStateAndNotify(int state) {
        mState = state;
        notifyState();
    }

    private int getState() {
        return mState;
    }

    private void notifyState() {
        if (mRecordCallback != null && mRecordCallback.get() != null) {
            try {
                mRecordCallback.get().onStateChanged(mState);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private class BinderService extends IRecordService.Stub {

        @Override
        public void startRecording() throws RemoteException {
            startRecordingInternal();
        }

        @Override
        public void pause() throws RemoteException {
            pauseInternal();
        }

        @Override
        public void resume() throws RemoteException {
            resumeInternal();
        }

        @Override
        public void stopRecording() throws RemoteException {
            stopRecordingInternal();
        }

        @Override
        public int getAmplitude() throws RemoteException {
            return getAmplitudeInternal();
        }

        @Override
        public int getState() throws RemoteException {
            return getStateInternal();
        }

        @Override
        public void registerCallback(IRecordCallback callback) throws RemoteException {
            registerCallbackInternal(callback);
        }

        @Override
        public void unregisterCallback() throws RemoteException {
            unregisterCallbackInternal();
        }
    }
}
