package com.dengzii.plugin.fund.api.bean;

import com.google.gson.annotations.SerializedName;

public class FundBean {
    private String fundCode;
    @SerializedName(value = "name", alternate = {"fund_name"})
    private String fundName;
    // ...
}
