package com.wang.mytest.feature.audio.playback;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

/**
 * 切换成听筒时，要向后 seek 2 秒，否则有一段静音
 */
public class PlaybackService extends LifecycleService {

    private static final String TAG = "PlaybackService";

    private AudioManager mAudioManager;
    private NotificationHelper mNotificationHelper;
    private EarpieceHelper mEarpieceHelper;

    private BinderService mBinderService;

    private MediaPlayer mMediaPlayer;
    private String mPlayingFile;
    private MutableLiveData<Integer> mState = new MutableLiveData<>(PlaybackManager.STATE_IDLE);
    private LiveData<Integer> mDistinctState = Transformations.distinctUntilChanged(mState);

    private List<IPlaybackCallback> mCallbacks = new CopyOnWriteArrayList<>();
    private Observer<? super Integer> mStateObserver = state -> {
        if (isPlaying()) {
            mEarpieceHelper.startWatching();
        } else {
            mEarpieceHelper.stopWatching();
        }

        if (mCallbacks != null) {
            mCallbacks.forEach(callback -> {
                try {
                    callback.onStateChanged(state);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    };

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = focusChange -> {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                startInternal();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                pauseInternal();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                break;
            default:
                break;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBinderService = new BinderService();
        mAudioManager = ContextCompat.getSystemService(this, AudioManager.class);
        mNotificationHelper = NotificationHelper.create(getApplicationContext());
        mEarpieceHelper = EarpieceHelper.create(getApplicationContext());
        mEarpieceHelper.getEarpiece().observe(this, isEarpiece -> {
            if (isEarpiece) {
                if (isPlaying()) {
                    int currentPosition = mMediaPlayer.getCurrentPosition();
                    int backward = (int) (currentPosition - TimeUnit.SECONDS.toMillis(2));
                    mMediaPlayer.seekTo(backward > 0 ? backward : currentPosition);
                }
            }
        });
        getLifecycle().addObserver(mNotificationHelper);
        getLifecycle().addObserver(mEarpieceHelper);

        mDistinctState.observeForever(mStateObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDistinctState.removeObserver(mStateObserver);

        getLifecycle().removeObserver(mNotificationHelper);
        getLifecycle().removeObserver(mEarpieceHelper);
        mCallbacks.clear();
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        super.onBind(intent);
        return mBinderService;
    }

    public void setDataSourceInternal(String file) {
        Log.d(TAG, "setDataSourceInternal: file = " + file);
        mPlayingFile = file;
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(mp -> {});
        mMediaPlayer.setOnErrorListener((mp, what, extra) -> {return true;});
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(file);
            mMediaPlayer.prepare();
            mState.setValue(PlaybackManager.STATE_PREPARED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startInternal() {
        Log.d(TAG, "startInternal: " + getStateInternal());
        if (isPrepared() || isPaused()) {
            int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
            Log.d(TAG, "startInternal: result = " + result);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer.start();
                mState.setValue(PlaybackManager.STATE_PLAYING);
            }
        }
    }

    public void pauseInternal() {
        if (isPlaying()) {
            mMediaPlayer.pause();
            mState.setValue(PlaybackManager.STATE_PAUSED);
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    public void stopInternal() {
        if (isPrepared() || isPaused() || isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mState.setValue(PlaybackManager.STATE_IDLE);
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    public int getStateInternal() {
        return Optional.ofNullable(mState.getValue()).orElse(PlaybackManager.STATE_IDLE);
    }

    public void addCallbackInternal(IPlaybackCallback callback) {
        mCallbacks.add(callback);
    }

    public void removeCallbackInternal(IPlaybackCallback callback) {
        mCallbacks.remove(callback);
    }

    private boolean isPrepared() {
        return getStateInternal() == PlaybackManager.STATE_PREPARED;
    }

    private boolean isPlaying() {
        return getStateInternal() == PlaybackManager.STATE_PLAYING;
    }

    private boolean isPaused() {
        return getStateInternal() == PlaybackManager.STATE_PAUSED;
    }

    private class BinderService extends IPlaybackService.Stub {

        @Override
        public void setDataSource(String file) throws RemoteException {
            setDataSourceInternal(file);
        }

        @Override
        public void start() throws RemoteException {
            startInternal();
        }

        @Override
        public void pause() throws RemoteException {
            pauseInternal();
        }

        @Override
        public void stop() throws RemoteException {
            stopInternal();
        }

        @Override
        public int getState() throws RemoteException {
            return getStateInternal();
        }

        @Override
        public void addCallback(IPlaybackCallback callback) throws RemoteException {
            addCallbackInternal(callback);
        }

        @Override
        public void removeCallback(IPlaybackCallback callback) throws RemoteException {
            removeCallbackInternal(callback);
        }
    }
}
