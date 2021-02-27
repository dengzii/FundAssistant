package com.dengzii.plugin.fund.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPollTask<T> implements PollTask, SubScribeSource<T> {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    private final List<Subscriber<T>> subscribers = new ArrayList<>();

    private boolean isStopped = true;

    @Override
    public void start(long durationMilliSec) {
        executor.scheduleAtFixedRate(() -> {
            T result = update();
            for (Subscriber<T> subscriber : subscribers) {
                subscriber.onUpdate(result);
            }
        }, 0, durationMilliSec, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        executor.shutdownNow();
        isStopped = true;
    }

    @Override
    public boolean isStopped() {
        return isStopped;
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    abstract T update();
}
