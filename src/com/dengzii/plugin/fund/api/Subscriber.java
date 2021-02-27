package com.dengzii.plugin.fund.api;

public interface Subscriber<T> {
    void onUpdate(T result);
}
