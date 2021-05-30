package com.wang.mytest.common.observer;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.wang.mytest.common.util.LogUtils;

public class ScreenObserver implements DefaultLifecycleObserver {

    private static final String TAG = "ScreenObserver";

    private final Context mContext;

    private Runnable mScreenOnListener;

    private Runnable mScreenOffListener;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (context == null || intent == null) {
                return;
            }
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            switch (action) {
                case Intent.ACTION_SCREEN_ON:
                    onScreenOn();
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    onScreenOff();
                    break;
                default:
                    break;
            }
        }

        private void onScreenOn() {
            if (mScreenOnListener != null) {
                mScreenOnListener.run();
            }
        }

        private void onScreenOff() {
            if (mScreenOffListener != null) {
                mScreenOffListener.run();
            }
        }
    };

    private ScreenObserver(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        IntentFilter filter = new IntentFilter();
        if (mScreenOnListener != null) {
            filter.addAction(Intent.ACTION_SCREEN_ON);
        }
        if (mScreenOffListener != null) {
            filter.addAction(Intent.ACTION_SCREEN_OFF);
        }
        mContext.registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
        mContext.unregisterReceiver(mReceiver);
    }

    public static Builder create(@NonNull Context context, @NonNull LifecycleOwner owner) {
        return new Builder(context, owner);
    }

    public static class Builder {
        private final Context mContext;
        private final LifecycleOwner mLifecycleOwner;
        private Runnable mScreenOnListener;
        private Runnable mScreenOffListener;

        private Builder(@NonNull Context context, @NonNull LifecycleOwner owner) {
            mContext = context;
            mLifecycleOwner = owner;
        }

        public Builder setScreenOnListener(Runnable screenOnListener) {
            mScreenOnListener = screenOnListener;
            return this;
        }

        public Builder setScreenOffListener(Runnable screenOffListener) {
            mScreenOffListener = screenOffListener;
            return this;
        }

        public void observe() {
            if (mLifecycleOwner.getLifecycle().getCurrentState() == DESTROYED) {
                LogUtils.warn(TAG, "observe: ignore");
                return;
            }
            if (mScreenOnListener == null && mScreenOffListener == null) {
                LogUtils.warn(TAG, "observe: nothing to observe");
                return;
            }
            ScreenObserver observer = new ScreenObserver(mContext);
            observer.mScreenOnListener = mScreenOnListener;
            observer.mScreenOffListener = mScreenOffListener;
            mLifecycleOwner.getLifecycle().addObserver(observer);
        }
    }
}
