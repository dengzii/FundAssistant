package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.StockUpdateBean;
import com.dengzii.plugin.fund.http.Http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SinaStockApi implements StockApi {

    @Override
    public AbstractPollTask<List<StockUpdateBean>> subscribeUpdate(List<String> stockList) {
        return new AbstractPollTask<List<StockUpdateBean>>() {
            @Override
            List<StockUpdateBean> update() {
                if (stockList.isEmpty()) {
                    return Collections.emptyList();
                }
                List<StockUpdateBean> updateBeans = new ArrayList<>(stockList.size());
                StringBuilder b = new StringBuilder("http://hq.sinajs.cn/list=");
                for (String s : stockList) {
                    b.append(s).append(",");
                }
                try {
                    String response = Http.getInstance().get(b.toString(), Charset.forName("gb18030"));
                    if (response == null) {
                        return Collections.emptyList();
                    }
                    response = response.replaceAll("(var hq_str_s[zh]\\d+=\")|(\";)", "");
                    String[] lines = response.split("\n");
                    if (lines.length != stockList.size()) {
                        return Collections.emptyList();
                    }
                    for (int i = 0; i < lines.length; i++) {
                        String line = lines[i];
                        String[] p = line.split(",");
                        StockUpdateBean bean = new StockUpdateBean();
                        bean.setCode(stockList.get(i));
                        bean.setName(p[0]);
                        bean.setTodayOpeningPrice(parseFloat(p[1]));
                        bean.setYesterdayClosingPrice(parseFloat(p[2]));
                        bean.setCurrentPrice(parseFloat(p[3]));
                        bean.setTodayHighPrice(parseFloat(p[4]));
                        bean.setTodayLowPrice(parseFloat(p[5]));
                        bean.setUnitPriceByOne(parseFloat(p[6]));
                        bean.setUnitPriceSaleOne(parseFloat(p[7]));
                        bean.setUnitPriceByTwo(parseFloat(p[13]));
                        bean.setPriceByTwo(parseFloat(p[12]));
                        bean.setDealShares(parseFloat(p[8]));
                        bean.setTotalTransactionAmount(parseFloat(p[9]));
                        bean.setDate(p[30]);
                        bean.setTime(p[31]);
                        updateBeans.add(bean);
                    }
                    System.out.println("SinaStockApi.update: " + updateBeans.size());
                    return updateBeans;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // ignore
                }
                return Collections.emptyList();
            }
        };
    }

    private static float parseFloat(String str) {

        try {
            return Float.parseFloat(str);
        } catch (Throwable e) {
            return 0.0f;
        }
    }
}
