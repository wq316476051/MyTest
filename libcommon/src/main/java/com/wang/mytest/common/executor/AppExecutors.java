package com.wang.mytest.common.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    public static final Executor MAIN = new MainExecutor();
    public static final Executor ASYNC = Executors.newCachedThreadPool();
    public static final Executor SERIAL = new SerialExecutor();

    private AppExecutors() {
    }
}
