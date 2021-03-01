package com.dengzii.plugin.fund.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPollTask<T> implements PollTask, SubScribeSource<T> {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    private final List<Subscriber<T>> subscribers = new ArrayList<>();

    private ScheduledFuture<?> future;

    @Override
    public void start(long durationMilliSec) {
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
        }
        future = executor.scheduleAtFixedRate(() -> {
            T result;
            try {
                result = update();
            } catch (Throwable t) {
                t.printStackTrace();
                return;
            }
            for (Subscriber<T> subscriber : subscribers) {
                subscriber.onUpdate(result);
            }
        }, 0, durationMilliSec, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        future.cancel(true);
    }

    @Override
    public boolean isStopped() {
        return future.isCancelled();
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void unsubscribeAll() {
        subscribers.clear();
    }

    abstract T update();
}
