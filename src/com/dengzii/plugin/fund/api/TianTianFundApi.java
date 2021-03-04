package com.dengzii.plugin.fund.api;

import com.dengzii.plugin.fund.api.bean.FundBean;
import com.dengzii.plugin.fund.api.bean.NetValueBean;
import com.dengzii.plugin.fund.http.Http;
import com.dengzii.plugin.fund.utils.GsonUtils;
import com.dengzii.plugin.fund.utils.Logger;
import com.google.gson.reflect.TypeToken;
import org.apache.http.impl.execchain.RequestAbortedException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author https://github.com/dengzii/
 */
public class TianTianFundApi implements FundApi {

    @Override
    public List<FundBean> getFundList() {
        try {
            String response = Http.getInstance().get("http://fund.eastmoney.com/js/fundcode_search.js").trim();
            if (response.isEmpty()) {
                return Collections.emptyList();
            }
            response = response.replaceAll("(var r = \\[)|( )|(];)|(\")", "");
            response = response.substring(2, response.length() - 1);
            String[] fund = response.split("],\\[");
            List<FundBean> fundBeans = new ArrayList<>();
            for (String f : fund) {
                String[] sp = f.split(",");
                FundBean fb = new FundBean();
                fb.setFundCode(sp[0]);
                fb.setPingYingAbbr(sp[1]);
                fb.setFundName(sp[2]);
                fb.setTypeName(sp[3]);
                fb.setPingYing(sp[4]);
                fundBeans.add(fb);
            }
            return fundBeans;
        } catch (InterruptedException | RequestAbortedException e) {
            // ignore
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public FundBean getFundNewestDetail(String fundCode) {
        try {
            String response = Http.getInstance().get(String.format("http://fundgz.1234567.com.cn/js/%s.js?rt=%d",
                    fundCode, System.currentTimeMillis()));
            if (response == null) {
                return null;
            }
            response = response.substring(8, response.length() - 2);
            Type t = new TypeToken<FundBean>() {
            }.getType();
            Logger.log("TianTianApi.getFundNewestDetail", response);
            return GsonUtils.fromJson(response, t);
        } catch (InterruptedException | RequestAbortedException e) {
            // ignore
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AbstractPollTask<List<FundBean>> updateFundList(final List<FundBean> fundBeans) {
        return new AbstractPollTask<List<FundBean>>() {
            @Override
            List<FundBean> update() {
                for (FundBean fundBean : fundBeans) {
                    try {
                        FundBean f = getFundNewestDetail(fundBean.getFundCode());
                        if (f == null) {
                            continue;
                        }
                        fundBean.setCutOffDate(f.getCutOffDate());
                        fundBean.setNetValue(f.getNetValue());
                        fundBean.setNetValueReckon(f.getNetValueReckon());
                        fundBean.setGrowthRateReckon(f.getGrowthRateReckon());
                        fundBean.setUpdateTime(f.getUpdateTime());

                        if (fundBean.getLast30DayNetValue() == null) {
                            List<NetValueBean> netValueHistory = getNetValueHistory(f.getFundCode(), 1, 30);
                            fundBean.setLast30DayNetValue(netValueHistory);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
                return fundBeans;
            }
        };
    }

    @Override
    public NetValueBean getNewestNetValue(String fundCode) {
        List<NetValueBean> h = getNetValueHistory(fundCode, 1, 1);
        if (h.isEmpty()) {
            return null;
        }
        return h.get(0);
    }

    @Override
    public List<NetValueBean> getNetValueHistory3Month(String fundCode) {

        List<NetValueBean> n = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            n.addAll(getNetValueHistory(fundCode, i + 1, 49));
        }
        return n;
    }

    @Override
    public List<NetValueBean> getNetValueHistory(String fundCode, int page, int pageSize) {
        String url = String.format("https://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&code=%s&page=%d&per=%d",
                fundCode, page, pageSize);
        List<NetValueBean> netValueBeans = new ArrayList<>();
        try {
            String response = Http.getInstance().get(url);
            response = response.replaceAll("(var apidata=\\{ content:\")|(\",records:1748,pages:36,curpage:1};)", "");
            response = response.substring(187);
            response = response.substring(0, response.length() - 21);
            response = response.replaceAll("(<td>)|" +
                    "(</td><td class='tor bold'>)|" +
                    "(</td><td class='tor bold (grn|red|bck)'>)|" +
                    "(</td><td>)|" +
                    "(</td><td class='red unbold'>)", ",");
            String[] lines = response.split("</tr><tr>");
            for (String line : lines) {
                String[] sp = line.split(",");
                NetValueBean bean = new NetValueBean();
                bean.setDate(sp[1]);
                bean.setNetValue(parseFloat(sp[2]));
                bean.setGrowthRate(parseFloat(sp[4]));
                bean.setSubscribeStatus(sp[5]);
                bean.setRedemptionStatus(sp[6]);
                bean.setDividend(sp[7].replaceAll("</td>", ""));
                netValueBeans.add(bean);
            }
        } catch (InterruptedException | RequestAbortedException e) {
            // ignore
        } catch (IOException e) {
            e.printStackTrace();
        }
        return netValueBeans;
    }

    private float parseFloat(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        try {
            return Float.parseFloat(s.replace("%", ""));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }
}
