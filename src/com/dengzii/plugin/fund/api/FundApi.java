package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;
import com.dengzii.plugin.fund.api.bean.NetValueBean;

import java.io.IOException;
import java.util.List;

/**
 * @author https://github.com/dengzii/
 */
public interface FundApi {
    List<FundBean> getFundList() throws IOException, InterruptedException;

    FundBean getFundNewestDetail(String fundCode);

    NetValueBean getNewestNetValue(String fundCode);

    AbstractPollTask<List<FundBean>> updateFundList(List<FundBean> fundBeans);

    List<NetValueBean> getNetValueHistory3Month(String fundCode);

    List<NetValueBean> getNetValueHistory(String fundCode, int page, int pageSize);

}
