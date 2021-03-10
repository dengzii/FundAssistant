package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.StockUpdateBean;

import java.util.List;

public interface StockApi {
    AbstractPollTask<List<StockUpdateBean>> subscribeUpdate(List<String> stockList);
}
