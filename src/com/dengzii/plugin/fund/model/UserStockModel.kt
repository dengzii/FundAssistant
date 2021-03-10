package com.dengzii.plugin.fund.model

import com.dengzii.plugin.fund.api.bean.StockUpdateBean

class UserStockModel(val stock: StockUpdateBean) {

    var buyPrice: Double = 0.0

    fun update(u: StockUpdateBean) {
        stock.name = u.name
        stock.code = u.code
        stock.time = u.time
        stock.date = u.date
        stock.totalTransactionAmount = u.totalTransactionAmount
        stock.dealShares = u.dealShares
        stock.todayLowPrice = u.todayLowPrice
        stock.todayHighPrice = u.todayHighPrice
        stock.priceByOne = u.priceByOne
        stock.priceByTwo = u.priceByTwo
        stock.unitPriceByOne = u.unitPriceByOne
        stock.unitPriceByTwo = u.unitPriceByTwo
        stock.unitPriceSaleOne = u.unitPriceSaleOne
        stock.unitPriceSaleTwo = u.unitPriceSaleTwo
        stock.yesterdayClosingPrice = u.yesterdayClosingPrice
        stock.currentPrice = u.currentPrice
    }
}