package com.dengzii.plugin.fund.conf

import com.dengzii.plugin.fund.model.UserFundModel
import java.text.DecimalFormat

class FundColConfig {

    val columns = mutableListOf(
        Col.FundName,
        Col.FundCode,
        Col.NetValueReckon,
        Col.FloatingRange,
        Col.UpdatedAt,
        Col.BuyingPrice,
        Col.TotalYield,
        Col.TotalGains,
        Col.HoldingShare,
        Col.GainsReckon
    )

    enum class Col(private val s: String) {
        FundCode("代码"),
        FundName("名称"),
        NetValueReckon("估算净值"),
        FloatingRange("估算浮动"),
        BuyingPrice("买入净值"),
        TotalYield("收益率"),
        TotalGains("总收益"),
        HoldingShare("持有份额"),
        GainsReckon("估算收益"),
        UpdatedAt("更新时间");


        companion object {
            private val format = DecimalFormat("###,###.##")
            private val formatPercent = DecimalFormat("###,###.##%")
        }

        fun getName(): String {
            return s
        }

        fun getAttr(model: UserFundModel): Any {
            return when (this) {
                FundCode -> model.fundBean.fundCode
                FundName -> model.fundBean.fundName
                FloatingRange -> model.growthRateReckon.formatPercent()
                NetValueReckon -> model.netValueReckon.format()
                HoldingShare -> model.holdingShare.format()
                UpdatedAt -> model.updatedAt
                BuyingPrice -> model.buyingPrice.format()
                TotalYield -> model.totalYield.formatPercent()
                TotalGains -> model.totalGains.format()
                GainsReckon -> model.gainsReckon.format()
            }
        }

        private fun Float.format() = format.format(this)
        private fun Float.formatPercent() = formatPercent.format(this)
    }
}