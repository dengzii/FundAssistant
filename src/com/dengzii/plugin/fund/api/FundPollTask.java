package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;

import java.util.List;

public class FundPollTask extends AbstractPollTask<List<FundBean>> {

    private final FundApi api;

    public static FundPollTask create(FundApiEnum api) {
        switch (api) {
            case Sina:
                return new FundPollTask(new SinaFundApi());
            case TianTian:
                return new FundPollTask(new TianTianFundApi());
            default:
                throw new IllegalArgumentException("unknown api");
        }
    }

    public FundPollTask(FundApi api) {
        this.api = api;
    }

    @Override
    List<FundBean> update() {
        return api.getFundList();
    }
}
