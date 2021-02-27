package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;
import com.dengzii.plugin.fund.api.bean.NetValueBean;
import junit.framework.TestCase;

import java.util.List;

public class TianTianFundApiTest extends TestCase {

    public void testGetNetValueHistory3Month() {

        TianTianFundApi api = new TianTianFundApi();
        List<NetValueBean> netValueBeans = api.getNetValueHistory3Month("160220");
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
        System.out.println(api.getNewestNetValue("160220"));

    }

    public void testTestGetNetValueHistory3Month() {
    }

    public void testGetNetValueHistory() {
    }

    public void testGetFundNewestDetail() {

        TianTianFundApi api = new TianTianFundApi();
        System.out.println(api.getFundNewestDetail("160220"));
    }
}