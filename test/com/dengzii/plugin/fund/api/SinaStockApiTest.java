package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.StockUpdateBean;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class SinaStockApiTest extends TestCase {

    public void testSubscribeUpdate() throws InterruptedException {
        AbstractPollTask<List<StockUpdateBean>> s = new SinaStockApi().subscribeUpdate(Arrays.asList("sz002157", "sz000001"));
        s.subscribe(result -> {
            for (StockUpdateBean stockUpdateBean : result) {
                System.out.println(stockUpdateBean.toString());
            }
        });
        s.start(2000);
        Thread.sleep(30000);
    }
}