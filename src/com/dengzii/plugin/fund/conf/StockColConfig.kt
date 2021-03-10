package com.dengzii.plugin.fund.conf

import com.dengzii.plugin.fund.model.UserStockModel
import java.text.DecimalFormat

/**
 * @author https://github.com/dengzii/
 */
class StockColConfig(val columns: List<Col>) {

    constructor() : this(
        mutableListOf(
            Col.StockCode,
            Col.StockName,
            Col.CurrentPrice,
            Col.DayHighPrice,
            Col.DayLowPrice,
            Col.PriceByOne,
            Col.DealShares,
            Col.TotalDealAmount,
            Col.UpdateDate,
            Col.UpdateTime,
        )
    )

    enum class Col(private val s: String) {
        StockCode("代码"),
        StockName("名称"),
        CurrentPrice("当前价格"),
        DayHighPrice("今日最高价"),
        DayLowPrice("今日最低价"),
        PriceByOne("买二"),
        DealShares("成交量"),
        TotalDealAmount("成交金额"),
        FloatGains("浮动收益"),
        Trending("日走势"),
        TodayGainsReckon("今日收益参考"),
        UpdateDate("更新日期"),
        UpdateTime("更新时间");

        companion object {
            private val format = DecimalFormat("###,###.##")
            private val formatPercent = DecimalFormat("###,###.##%")
            private val valueMap = values().associateBy { it.s }

            fun getByName(s: String): Col {
                return valueMap[s]!!
            }
        }

        fun getName(): String {
            return s
        }

        fun getAttr(model: UserStockModel): Any {
            val bean = model.stock
            try {
                return when (this) {
                    StockCode -> bean.code
                    StockName -> bean.name
                    CurrentPrice -> bean.currentPrice.format()
                    DayHighPrice -> bean.todayHighPrice.format()
                    DayLowPrice -> bean.todayLowPrice.format()
                    PriceByOne -> bean.priceByTwo.format()
                    DealShares -> bean.dealShares.format()
                    TotalDealAmount -> bean.totalTransactionAmount.format()
                    FloatGains -> ""
                    Trending -> ""
                    TodayGainsReckon -> ""
                    UpdateDate -> bean.date
                    UpdateTime -> bean.time
                }
            } catch (e: Throwable) {
                return "-"
            }
        }

        private fun Float.format() = format.format(this)
        private fun Float.formatPercent() = formatPercent.format(this / 100)
    }
}