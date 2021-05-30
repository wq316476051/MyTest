package com.wang.mytest.common.executor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;

public class SerialExecutor implements Executor {

    private final Queue<Runnable> mTasks = new LinkedList<>();

    private Runnable mExecuting;

    public synchronized boolean isEmpty() {
        return mTasks.isEmpty();
    }

    public synchronized void execute(final Runnable command) {
        mTasks.offer(() -> {
            try {
                command.run();
            } finally {
                scheduleNext();
            }
        });
        if (mExecuting == null) {
            scheduleNext();
        }
    }

    private synchronized void scheduleNext() {
        mExecuting = mTasks.poll();
        if (mExecuting != null) {
            AppExecutors.ASYNC.execute(mExecuting);
        }
    }
}
