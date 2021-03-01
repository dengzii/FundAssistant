package com.dengzii.plugin.fund.model

import com.dengzii.plugin.fund.api.bean.FundBean
import kotlin.random.Random

class UserFundModel() {

    lateinit var fundBean: FundBean
    var growthRateReckon = 0f
    var buyingPrice = 0f
    var totalYield = 0f
    var totalGains = 0f
    var holdingShare = 0f
    var netValueReckon = 0f
    var gainsReckon = 0f
    var updatedAt = ""

    var addDate = 0L
    var gainsAfterAdded = 0f
    var gainsWeek = 0f
    var gainsMonth = 0f

    constructor(fundBean: FundBean) : this() {
        this.fundBean = fundBean
    }

    fun updateFund(updated: FundBean) {
        fundBean.cutOffDate = updated.cutOffDate
        fundBean.growthRateReckon = updated.growthRateReckon
        fundBean.netValue = updated.netValue
        fundBean.updateTime = updated.updateTime
        fundBean.netValueReckon = updated.netValueReckon
    }

    fun rand() {
        this.fundBean = FundBean().apply {
            fundName = "诺安成长混合"
            fundCode = "2300042"
        }
        val r = Random(System.nanoTime())
        this.buyingPrice = r.nextInt(1, 7) * 1.34f
        this.gainsReckon = r.nextFloat()
        this.totalGains = r.nextFloat() * r.nextInt(-500, 1000)
        this.holdingShare = r.nextInt(100, 200) * 1.32f
        this.totalYield = r.nextFloat() * r.nextInt(-30, 30)
        this.updatedAt = "2-26 15:51"
        this.growthRateReckon = r.nextFloat() * 5
        this.netValueReckon = r.nextFloat() * 5
    }

}