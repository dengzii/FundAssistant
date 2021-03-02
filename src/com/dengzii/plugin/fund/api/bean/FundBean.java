package com.dengzii.plugin.fund.api.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author https://github.com/dengzii/
 */
public class  FundBean {

    @SerializedName(value = "fundcode", alternate = {"fundCode"})
    private String fundCode;
    @SerializedName(value = "name", alternate = {"fund_name"})
    private String fundName;

    private String typeName;
    private String pingYingAbbr;
    private String pingYing;

    @SerializedName("jzrq")
    private String cutOffDate;
    @SerializedName("dwjz")
    private float netValue;
    @SerializedName("gsz")
    private float netValueReckon;
    @SerializedName("gszzl")
    private float growthRateReckon;
    @SerializedName("gztime")
    private String updateTime;

    public FundBean(String fundCode){
        this.fundCode = fundCode;
    }

    public FundBean(){
    }

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPingYingAbbr() {
        return pingYingAbbr;
    }

    public void setPingYingAbbr(String pingYingAbbr) {
        this.pingYingAbbr = pingYingAbbr;
    }

    public String getPingYing() {
        return pingYing;
    }

    public void setPingYing(String pingYing) {
        this.pingYing = pingYing;
    }

    public String getCutOffDate() {
        return cutOffDate;
    }

    public void setCutOffDate(String cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

    public float getNetValue() {
        return netValue;
    }

    public void setNetValue(float netValue) {
        this.netValue = netValue;
    }

    public float getNetValueReckon() {
        return netValueReckon;
    }

    public void setNetValueReckon(float netValueReckon) {
        this.netValueReckon = netValueReckon;
    }

    public float getGrowthRateReckon() {
        return growthRateReckon;
    }

    public void setGrowthRateReckon(float growthRateReckon) {
        this.growthRateReckon = growthRateReckon;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FundBean{" +
                "fundCode='" + fundCode + '\'' +
                ", fundName='" + fundName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", pingYingAbbr='" + pingYingAbbr + '\'' +
                ", pingYing='" + pingYing + '\'' +
                ", cutOffDate='" + cutOffDate + '\'' +
                ", netValue=" + netValue +
                ", netValueReckon=" + netValueReckon +
                ", growthRateReckon=" + growthRateReckon +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
