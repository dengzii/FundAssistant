package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;
import com.dengzii.plugin.fund.api.bean.NetValueBean;

import java.util.Collections;
import java.util.List;

/**
 * @author https://github.com/dengzii/
 */
public class SinaFundApi implements FundApi {
    @Override
    public List<FundBean> getFundList() {
        return Collections.emptyList();
    }

    @Override
    public FundBean getFundNewestDetail(String fundCode) {
        return null;
    }

    @Override
    public NetValueBean getNewestNetValue(String fundCode) {
        return null;
    }

    @Override
    public AbstractPollTask<List<FundBean>> updateFundList(List<FundBean> fundBeans) {
        return null;
    }

    @Override
    public List<NetValueBean> getNetValueHistory3Month(String fundCode) {
        return null;
    }

    @Override
    public List<NetValueBean> getNetValueHistory(String fundCode, int page, int pageSize) {
        return null;
    }
}
