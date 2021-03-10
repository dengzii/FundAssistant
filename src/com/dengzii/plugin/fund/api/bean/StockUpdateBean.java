package com.dengzii.plugin.fund.api.bean;

public class StockUpdateBean {

    private String name;
    private String code;
    private float todayOpeningPrice;
    private float yesterdayClosingPrice;
    private float currentPrice;
    private float todayTopPrice;
    private float todayLowPrice;
    private float dealShares;
    private float totalTransactionAmount;
    private float unitPriceByOne;
    private float priceByOne;
    private float unitPriceByTwo;
    private float priceByTwo;

    private float unitPriceSaleOne;
    private float priceSaleOne;
    private float unitPriceSaleTwo;
    private float priceSaleTwo;

    private String date;
    private String time;

    public float getPriceSaleOne() {
        return priceSaleOne;
    }

    public void setPriceSaleOne(float priceSaleOne) {
        this.priceSaleOne = priceSaleOne;
    }

    public float getUnitPriceSaleTwo() {
        return unitPriceSaleTwo;
    }

    public void setUnitPriceSaleTwo(float unitPriceSaleTwo) {
        this.unitPriceSaleTwo = unitPriceSaleTwo;
    }

    public float getPriceSaleTwo() {
        return priceSaleTwo;
    }

    public void setPriceSaleTwo(float priceSaleTwo) {
        this.priceSaleTwo = priceSaleTwo;
    }

    public float getUnitPriceSaleOne() {
        return unitPriceSaleOne;
    }

    public void setUnitPriceSaleOne(float unitPriceSaleOne) {
        this.unitPriceSaleOne = unitPriceSaleOne;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTodayOpeningPrice() {
        return todayOpeningPrice;
    }

    public void setTodayOpeningPrice(float todayOpeningPrice) {
        this.todayOpeningPrice = todayOpeningPrice;
    }

    public float getYesterdayClosingPrice() {
        return yesterdayClosingPrice;
    }

    public void setYesterdayClosingPrice(float yesterdayClosingPrice) {
        this.yesterdayClosingPrice = yesterdayClosingPrice;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getTodayHighPrice() {
        return todayTopPrice;
    }

    public void setTodayHighPrice(float todayTopPrice) {
        this.todayTopPrice = todayTopPrice;
    }

    public float getTodayLowPrice() {
        return todayLowPrice;
    }

    public void setTodayLowPrice(float todayLowPrice) {
        this.todayLowPrice = todayLowPrice;
    }

    public float getDealShares() {
        return dealShares;
    }

    public void setDealShares(float dealShares) {
        this.dealShares = dealShares;
    }

    public float getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    public void setTotalTransactionAmount(float totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }

    public float getUnitPriceByOne() {
        return unitPriceByOne;
    }

    public void setUnitPriceByOne(float unitPriceByOne) {
        this.unitPriceByOne = unitPriceByOne;
    }

    public float getPriceByOne() {
        return priceByOne;
    }

    public void setPriceByOne(float priceByOne) {
        this.priceByOne = priceByOne;
    }

    public float getUnitPriceByTwo() {
        return unitPriceByTwo;
    }

    public void setUnitPriceByTwo(float unitPriceByTwo) {
        this.unitPriceByTwo = unitPriceByTwo;
    }

    public float getPriceByTwo() {
        return priceByTwo;
    }

    public void setPriceByTwo(float priceByTwo) {
        this.priceByTwo = priceByTwo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "StockUpdateBean{" +
                "name='" + name + '\'' +
                ", todayOpeningPrice=" + todayOpeningPrice +
                ", currentPrice=" + currentPrice +
                ", todayTopPrice=" + todayTopPrice +
                ", todayLowPrice=" + todayLowPrice +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
