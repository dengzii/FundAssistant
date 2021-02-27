package com.dengzii.plugin.fund.api;

public interface SubScribeSource<T> {
    void subscribe(Subscriber<T> subscriber);

    void unsubscribe(Subscriber<T> subscriber);
}
