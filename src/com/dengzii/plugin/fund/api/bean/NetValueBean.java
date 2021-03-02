package com.dengzii.plugin.fund.api.bean;

/**
 * @author https://github.com/dengzii/
 */
public class  NetValueBean {
    private String date;
    private float netValue;
    private float growthRate;
    private String subscribeStatus;
    private String redemptionStatus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getNetValue() {
        return netValue;
    }

    public void setNetValue(float netValue) {
        this.netValue = netValue;
    }

    public float getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(float growthRate) {
        this.growthRate = growthRate;
    }

    public String getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(String subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public String getRedemptionStatus() {
        return redemptionStatus;
    }

    public void setRedemptionStatus(String redemptionStatus) {
        this.redemptionStatus = redemptionStatus;
    }

    @Override
    public String toString() {
        return "NetValueBean{" +
                "date='" + date + '\'' +
                ", netValue=" + netValue +
                ", growthRate=" + growthRate +
                ", subscribeStatus='" + subscribeStatus + '\'' +
                ", redemptionStatus='" + redemptionStatus + '\'' +
                '}';
    }
}
