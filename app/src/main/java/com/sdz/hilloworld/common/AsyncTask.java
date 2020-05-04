package com.sdz.hilloworld.common;

import java.util.concurrent.ScheduledExecutorService;

public abstract class AsyncTask {
    private static volatile AsyncTask mInstance;
    private static final String TAG = "AsyncTask";
    ScheduledExecutorService mService;


    //此处使用双重判定实现单例
    public static AsyncTask getInstance() {
        if (mInstance == null) {
            synchronized (AsyncTask.class) {
                if (mInstance == null) {
                    mInstance = new AsyncTaskImp();
                }
            }
        }
        return mInstance;
    }

    Runnable wrapTask(final Runnable task, AsyncTaskCallback callback) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                    if (callback != null) {
                        callback.onFinish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public abstract void postNormalTask(Runnable task, AsyncTaskCallback callback);

    public abstract void postNormalTask(Runnable task);

    public abstract void postScheduleTask(int taskId, Runnable task, long initTime, long periodTime);

    public abstract void cancelScheduleTask(final int taskId, boolean immediately);
}
