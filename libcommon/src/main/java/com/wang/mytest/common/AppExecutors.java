package com.wang.mytest.common;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    public static final Executor MAIN = new MainExecutor();
    public static final Executor ASYNC = Executors.newCachedThreadPool();
    public static final Executor SERIAL = new SerialExecutor();

    private AppExecutors() {
    }

    private static class MainExecutor implements Executor {

        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mHandler.post(command);
        }
    }

    private static class SerialExecutor implements Executor {

        private final Deque<Runnable> mTasks = new LinkedList<>();

        private Runnable mActive;

        public synchronized void execute(final Runnable command) {
            mTasks.offer(() -> {
                try {
                    command.run();
                } finally {
                    scheduleNext();
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        private synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                ASYNC.execute(mActive);
            }
        }
    }
}
