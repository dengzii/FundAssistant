package com.dengzii.plugin.fund.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class AbstractTask<T> implements UpdateTask<T>, UpdateSubScribeSource<T> {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final List<UpdateSubscriber<T>> subscribers = new ArrayList<>();

    @Override
    public void start() {
        executor.execute(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T result = update();
                for (UpdateSubscriber<T> subscriber : subscribers) {
                    subscriber.onUpdate(result);
                }
            }
        });
    }

    @Override
    public void subscribe(UpdateSubscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(UpdateSubscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    abstract T update();
}
