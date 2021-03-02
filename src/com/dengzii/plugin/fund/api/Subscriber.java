package com.dengzii.plugin.fund.api;

/**
 * @author https://github.com/dengzii/
 */
public interface Subscriber<T> {
    void onUpdate(T result);
}
