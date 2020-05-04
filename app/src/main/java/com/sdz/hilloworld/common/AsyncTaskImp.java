package com.sdz.hilloworld.common;

import com.sdz.hilloworld.utils.Log;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class AsyncTaskImp extends AsyncTask {

    private static final String TAG = "AsyncTaskImp";
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private HashMap<Integer, ScheduledFuture> taskMap;

    AsyncTaskImp() {
        mService = Executors.newScheduledThreadPool(5, new DemoThreadFactory());
        taskMap = new HashMap<>();
    }


    @Override
    public void postNormalTask(Runnable task, AsyncTaskCallback callback) {
        if (isClosed.get()) {
            Log.i(TAG, "ExecutorsService is Closed!");
            return;
        }
        task = wrapTask(task, callback);
        mService.execute(task);
    }

    @Override
    public void postNormalTask(Runnable task) {
        postNormalTask(task, null);
    }

    @Override
    public void postScheduleTask(final int taskId, Runnable task, long initTime, long periodTime) {
        if (isClosed.get()) {
            Log.i(TAG, "ExecutorsService is Closed!");
            return;
        }
        if (taskMap.get(taskId) != null) {
            cancelScheduleTask(taskId, true);
        }
        task = wrapTask(task, null);
        ScheduledFuture<?> scheduledFuture = mService.scheduleAtFixedRate(task, initTime, periodTime, TimeUnit.MILLISECONDS);
        taskMap.put(taskId, scheduledFuture);
    }

    @Override
    public void cancelScheduleTask(final int taskId, boolean immediately) {
        ScheduledFuture scheduledFuture = taskMap.get(taskId);
        if (scheduledFuture != null) {
            if (!scheduledFuture.isCancelled()) {
                scheduledFuture.cancel(immediately);
                taskMap.remove(taskId);
            }
        }
    }

    private static class DemoThreadFactory implements ThreadFactory {
        private final static String THREAD_TAG = "demo-thread-";

        private final AtomicInteger mCount = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            try {
                return new Thread(runnable, THREAD_TAG + mCount.getAndIncrement());
            } catch (Exception e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e) {
                Log.i(TAG, "memory not enough, create thread failed.");
            }
            return null;
        }
    }
}