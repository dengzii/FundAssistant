package com.dengzii.plugin.fund.conf

import com.dengzii.plugin.fund.model.UserFundModel
import java.text.DecimalFormat

/**
 * @author https://github.com/dengzii/
 */
class FundColConfig(val columns: List<Col>) {

    constructor() : this(
        mutableListOf(
            Col.FundName,
            Col.FundCode,
            Col.CurrentNetValue,
            Col.NetValueReckon,
            Col.GrowthRateReckon,
            Col.UpdateTime,
            Col.Last30DayNetValue,
//        Col.BuyingPrice,
//        Col.TotalYield,
//        Col.TotalGains,
//        Col.HoldingShare,
//        Col.GainsReckon
        )
    )


    enum class Col(private val s: String) {
        FundCode("代码"),
        FundName("名称"),
        CurrentNetValue("当前净值"),
        NetValueReckon("估算净值"),
        GrowthRateReckon("估算浮动"),
        Last30DayNetValue("30日净值"),
        BuyingPrice("买入净值"),
        TotalYield("收益率"),
        TotalGains("总收益"),
        HoldingShare("持有份额"),
        GainsReckon("估算收益"),
        UpdateTime("更新时间");

        companion object {
            private val format = DecimalFormat("###,###.##")
            private val formatPercent = DecimalFormat("###,###.##%")
            val valueMap = values().associateBy { it.s }

            fun getByName(s: String): Col {
                return valueMap[s]!!
            }
        }

        fun getName(): String {
            return s
        }

        fun getAttr(model: UserFundModel): Any {
            return when (this) {
                FundCode -> model.fundBean.fundCode
                FundName -> model.fundBean.fundName
                CurrentNetValue -> model.fundBean.netValue
                UpdateTime -> model.fundBean.updateTime ?: "-"
                GrowthRateReckon -> model.fundBean.growthRateReckon.formatPercent()
                NetValueReckon -> model.fundBean.netValueReckon.format()
                HoldingShare -> model.holdingShare.format()
                BuyingPrice -> model.buyingPrice.format()
                TotalYield -> model.totalYield.formatPercent()
                TotalGains -> model.totalGains.format()
                GainsReckon -> model.gainsReckon.format()
                Last30DayNetValue -> model.fundBean.last30DayNetValue ?: "-"
            }
        }

        private fun Float.format() = format.format(this)
        private fun Float.formatPercent() = formatPercent.format(this / 100)
    }
}