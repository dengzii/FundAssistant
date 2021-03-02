package com.dengzii.plugin.fund.api;

/**
 * @author https://github.com/dengzii/
 */
public interface PollTask {
    void start(long durationMilliSec);

    void stop();

    boolean isStopped();
}
