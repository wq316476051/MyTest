package com.wang.mytest.common.observer;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.wang.mytest.common.util.LogUtils;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;

/**
 * Application {
 *      onCreate {
 *          LocaleChangeObserver.register(Application, this::onLocaleChanged)
 *      }
 * }
 *
 * AppCompatActivity {
 *     onCreate {
 *         LocaleChangeObserver.create(this, this)
 *                 .setListener(this::onLocaleChanged)
 *                 .observe()
 *     }
 * }
 */
public class LocaleChangeObserver implements DefaultLifecycleObserver {

    private static final String TAG = "LocaleChangeObserver";

    private final Context mContext;
    private Runnable mLocalChangeListener;

    private final BroadcastReceiver mLocalChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mLocalChangeListener != null) {
                mLocalChangeListener.run();
            }
        }
    };

    private LocaleChangeObserver(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mLocalChangeReceiver, intentFilter);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mLocalChangeReceiver);
    }

    public static void register(@NonNull Application application, @Nullable Runnable onLocaleChangedListener) {
        application.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (onLocaleChangedListener != null) {
                    onLocaleChangedListener.run();
                }
                LocalBroadcastManager.getInstance(application)
                        .sendBroadcast(new Intent(Intent.ACTION_LOCALE_CHANGED));
            }
        }, new IntentFilter(Intent.ACTION_LOCALE_CHANGED));
    }

    public static Builder create(@NonNull Context context, @NonNull LifecycleOwner owner) {
        return new Builder(context, owner);
    }

    public static class Builder {
        private final Context mContext;
        private final LifecycleOwner mLifecycleOwner;
        private Runnable mLocalChangeListener;

        private Builder(@NonNull Context context, @NonNull LifecycleOwner owner) {
            mContext = context;
            mLifecycleOwner = owner;
        }

        public Builder setListener(Runnable localChangeListener) {
            mLocalChangeListener = localChangeListener;
            return this;
        }

        public void observe() {
            if (mLifecycleOwner.getLifecycle().getCurrentState() == DESTROYED) {
                LogUtils.warn(TAG, "observe: ignore");
                return;
            }
            if (mLocalChangeListener == null) {
                LogUtils.warn(TAG, "observe: nothing to observe");
                return;
            }
            LocaleChangeObserver observer = new LocaleChangeObserver(mContext);
            observer.mLocalChangeListener = mLocalChangeListener;
            mLifecycleOwner.getLifecycle().addObserver(observer);
        }
    }
}
