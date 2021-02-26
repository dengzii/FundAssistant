package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;

import java.util.Collections;
import java.util.List;

public class SinaFundApi implements FundApi {
    @Override
    public List<FundBean> getFundList() {
        return Collections.emptyList();
    }

    @Override
    public List<FundBean> searchFund(String keywords) {
        return null;
    }
}
