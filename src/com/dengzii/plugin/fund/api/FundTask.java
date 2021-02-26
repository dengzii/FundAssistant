package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;

import java.util.List;

public class FundTask extends AbstractTask<List<FundBean>> {

    private final FundApi api;

    public FundTask(FundApi api) {
        this.api = api;
    }

    @Override
    List<FundBean> update() {
        return api.getFundList();
    }
}
