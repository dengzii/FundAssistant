package com.dengzii.plugin.fund;

import com.dengzii.plugin.fund.api.FundApi;
import com.dengzii.plugin.fund.api.SinaFundApi;
import com.dengzii.plugin.fund.api.bean.FundBean;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class FundApiTest {

    @Test
    public void sinaApiTest() {
        FundApi api = new SinaFundApi();
        List<FundBean> funds = api.getFundList();

        Assert.assertNotNull(funds);
        Assert.assertNotEquals(funds.size(), 0);
    }

    @Test
    public void tianTianApiTest() {

    }
}
