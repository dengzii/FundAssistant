package com.dengzii.plugin.fund.api;

/**
 * @author https://github.com/dengzii/
 */
public interface SubScribeSource<T> {
    void subscribe(Subscriber<T> subscriber);

    void unsubscribe(Subscriber<T> subscriber);

    void unsubscribeAll();
}
