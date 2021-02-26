package com.dengzii.plugin.fund.api;

public interface UpdateSubScribeSource<T> {
    void subscribe(UpdateSubscriber<T> subscriber);

    void unsubscribe(UpdateSubscriber<T> subscriber);
}
