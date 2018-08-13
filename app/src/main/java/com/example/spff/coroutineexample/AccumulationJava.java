package com.example.spff.coroutineexample;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


public class AccumulationJava implements Accumulation {
    private final String TAG = getClass().getSimpleName();

    private LongRunningCallbackOne longRunningCallbackOne;

    public AccumulationJava(LongRunningCallbackOne longRunningCallbackOne) {
        this.longRunningCallbackOne = longRunningCallbackOne;
    }

    //https://stackoverflow.com/questions/2180419/wrapping-an-asynchronous-computation-into-a-synchronous-blocking-computation
    public class AdditionFuture implements Future<Integer> {

        private volatile Integer result = null;
        private volatile boolean cancelled = false;
        private final CountDownLatch countDownLatch;

        public AdditionFuture() {
            countDownLatch = new CountDownLatch(1);
        }

        @Override
        public boolean cancel(final boolean mayInterruptIfRunning) {
            if (isDone()) {
                return false;
            } else {
                countDownLatch.countDown();
                cancelled = true;
                return !isDone();
            }
        }

        @Override
        public Integer get() throws InterruptedException {
            countDownLatch.await();
            return result;
        }

        @Override
        public Integer get(final long timeout, @NonNull final TimeUnit unit)
                throws InterruptedException {
            countDownLatch.await(timeout, unit);
            return result;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public boolean isDone() {
            return countDownLatch.getCount() == 0;
        }

        public void onOneResult(final Integer result) {
            this.result = result;
            countDownLatch.countDown();
        }

    }

    private Future<Integer> doSomething() {
        AdditionFuture future = new AdditionFuture();
        longRunningCallbackOne.one(future::onOneResult);
        return future;
    }

    public class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            return doSomething().get();
        }
    }

    @Override
    public void accumulate(int times) {


        long startTime = System.currentTimeMillis();

        //https://blog.csdn.net/mazhimazh/article/details/19291965


        final ArrayList<FutureTask<Integer>> futureTasks = new ArrayList<>();

        for (int i = 0;i < times + 1;i++){
            futureTasks.add(new FutureTask<>(new MyCallable()));
        }

        ExecutorService executor = Executors.newFixedThreadPool(times + 1);
        //ExecutorService executor = Executors.newFixedThreadPool(1);

        for (FutureTask<Integer> futureTask: futureTasks) {
            executor.execute(futureTask);
        }

        while (true) {
            try {
                int size = 0;
                for (FutureTask<Integer> futureTask: futureTasks) {//  两个任务都完成
                    if (!futureTask.isDone()) {
                        size++;
                    }
                }

                if(size == 0) {
                    executor.shutdown();
                    break;
                }
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int a = 0;
        try {
            for (FutureTask<Integer> futureTask : futureTasks) {
                a += futureTask.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        Log.i(TAG, "The answer is " + a);
        long estimatedTime = System.currentTimeMillis() - startTime;
        Log.i(TAG, "Java Completed in" + estimatedTime + "ms");


    }


}
