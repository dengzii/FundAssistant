package com.dengzii.plugin.fund.api.bean;

import com.google.gson.annotations.SerializedName;

public class FundBean {
    private String fundCode;
    @SerializedName(value = "name", alternate = {"fund_name"})
    private String fundName;
    // ...

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }
}
