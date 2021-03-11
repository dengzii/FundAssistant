package com.dengzii.plugin.fund.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author https://github.com/dengzii/
 */
public abstract class AbstractPollTask<T> implements PollTask, SubScribeSource<T> {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
    private final List<Subscriber<T>> subscribers = new ArrayList<>();

    private ScheduledFuture<?> future;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private final Calendar nowTime = Calendar.getInstance();
    private Calendar beginTime;
    private Calendar endTime;

    public AbstractPollTask() {
        try {
            Date begin = dateFormat.parse("9:00");
            Date end = dateFormat.parse("15:00");
            beginTime = Calendar.getInstance();
            beginTime.setTime(begin);
            endTime = Calendar.getInstance();
            endTime.setTime(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

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
                checkStop();
                return;
            }
            for (Subscriber<T> subscriber : subscribers) {
                subscriber.onUpdate(result);
            }
            checkStop();
        }, 0, durationMilliSec, TimeUnit.MILLISECONDS);
    }

    private void checkStop(){
        if (!isTimeRange()) {
            stop();
        }
    }

    private boolean isTimeRange() {
        Date now = null;
        try {
            now = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            return false;
        }
        nowTime.setTime(now);
        return nowTime.before(endTime) && nowTime.after(beginTime);
    }

    @Override
    public void stop() {
        try {
            future.cancel(true);
        }catch (Throwable t){
            t.printStackTrace();
        }
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
