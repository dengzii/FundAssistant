package com.dengzii.plugin.fund;

import com.dengzii.plugin.fund.api.*;
import com.dengzii.plugin.fund.api.bean.FundBean;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpClient;
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
    public void singApiSubscribeTest() {

        AbstractPollTask<List<FundBean>> task = new FundPollTask(new SinaFundApi());
        task.subscribe(new UpdateSubscriber<List<FundBean>>() {
            @Override
            public void onUpdate(List<FundBean> result) {
                Assert.assertNotNull(result);
            }
        });
        task.start();
    }
    @Test
    public void tianTianApiTest() {

    }
}
