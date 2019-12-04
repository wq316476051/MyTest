package com.wang.mytest.feature.audio.playback;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class PlaybackManager {

    private static final String TAG = "PlaybackManager";

    public static final int STATE_IDLE = 0;
    public static final int STATE_PREPARED = 1;
    public static final int STATE_PLAYING = 2;
    public static final int STATE_PAUSED = 3;

    private IPlaybackService mPlaybackService;

    private MutableLiveData<Boolean> mConnected = new MutableLiveData<>(false);

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlaybackService = IPlaybackService.Stub.asInterface(service);
            mConnected.postValue(mPlaybackService != null);
        }

        public void onServiceDisconnected(ComponentName name) {
            mConnected.postValue(false);
        }
    };

    private PlaybackManager(Context context) {
        Intent intent = new Intent(context, PlaybackService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public static PlaybackManager create(@NonNull Context context) {
        return new PlaybackManager(context);
    }

    public LiveData<Boolean> getConnectState() {
        return Transformations.distinctUntilChanged(mConnected);
    }

    public boolean isConnected() {
        return Optional.ofNullable(mConnected.getValue()).orElse(false);
    }

    public void setDataSource(String file) {
        if (isConnected()) {
            try {
                mPlaybackService.setDataSource(file);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (isConnected()) {
            try {
                mPlaybackService.start();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (isConnected()) {
            try {
                mPlaybackService.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (isConnected()) {
            try {
                mPlaybackService.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int getState() {
        if (isConnected()) {
            try {
                return mPlaybackService.getState();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return STATE_IDLE;
    }

    public void addCallback(IPlaybackCallback callback) {
        if (isConnected()) {
            try {
                mPlaybackService.addCallback(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCallback(IPlaybackCallback callback) {
        if (isConnected()) {
            try {
                mPlaybackService.removeCallback(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPrepared() {
        return getState() == STATE_PREPARED;
    }

    public boolean isAtLeastPrepared() {
        return getState() >= STATE_PREPARED;
    }

    public boolean isPlaying() {
        return getState() == STATE_PLAYING;
    }

    public boolean isPaused() {
        return getState() == STATE_PAUSED;
    }
}
