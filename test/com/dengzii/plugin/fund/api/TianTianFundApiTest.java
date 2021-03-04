package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;
import com.dengzii.plugin.fund.api.bean.NetValueBean;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author https://github.com/dengzii/
 */
public class  TianTianFundApiTest extends TestCase {

    public void testGetNetValueHistory3Month() {

        TianTianFundApi api = new TianTianFundApi();
        List<NetValueBean> netValueBeans = api.getNetValueHistory3Month("000235");
        for (NetValueBean netValueBean : netValueBeans) {
            System.out.println(netValueBean);
        }
    }

    public void testGetAllFunds() {

        TianTianFundApi api = new TianTianFundApi();
        List<FundBean> allFunds = api.getFundList();
        System.out.println(allFunds);
    }

    public void testGetNewestNetValue() {

        TianTianFundApi api = new TianTianFundApi();
        System.out.println(api.getNewestNetValue("000235"));

    }

    public void testTestGetNetValueHistory3Month() {
    }

    public void testGetNetValueHistory() {
    }

    public void testGetFundNewestDetail() {

        TianTianFundApi api = new TianTianFundApi();
        System.out.println(api.getFundNewestDetail("160220"));
    }

    public void testUpdateFundList() throws InterruptedException {

        List<FundBean> fundBeans = new ArrayList<>();
        fundBeans.add(new FundBean("160220"));
        fundBeans.add(new FundBean("000001"));
        fundBeans.add(new FundBean("000002"));

        TianTianFundApi api = new TianTianFundApi();
        AbstractPollTask<List<FundBean>> pollTask = api.updateFundList(fundBeans);
        pollTask.subscribe(result -> {
            for (FundBean fundBean : result) {
                System.out.println(fundBean);
            }
        });

        pollTask.start(500);
        Thread.sleep(20000);
        pollTask.stop();
        Thread.sleep(10000);
    }
}