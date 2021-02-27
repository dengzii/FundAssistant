package com.dengzii.plugin.fund.api;

public interface PollTask {
    void start(long durationMilliSec);

    void stop();

    boolean isStopped();
}
