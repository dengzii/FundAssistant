package com.dengzii.plugin.fund.api;

public interface UpdateSubscriber<T> {
    void onUpdate(T result);
}
