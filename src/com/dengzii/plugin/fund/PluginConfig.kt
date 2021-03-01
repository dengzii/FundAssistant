package com.dengzii.plugin.fund

import com.dengzii.plugin.fund.api.bean.FundBean
import com.dengzii.plugin.fund.conf.FundColConfig
import com.dengzii.plugin.fund.model.FundGroup
import com.dengzii.plugin.fund.tools.PersistentConfig
import com.dengzii.plugin.fund.utils.GsonUtils
import com.google.gson.reflect.TypeToken

object PluginConfig : PersistentConfig() {

    var fundColConfig by persistentProperty(FundColConfig(), "FundColConfig1")
    private var mFundGroupsPer by persistentProperty("{}", "FundGroups1")
    private var mAllFunds by persistentProperty("[]", "AllFundList1")

    private var mFundGroups1: Map<String, FundGroup>? = null

    fun loadFundGroups(): Map<String, FundGroup> {
        val t = object : TypeToken<Map<String, FundGroup>>() {}.type
        return GsonUtils.fromJson(mFundGroupsPer!!, t)
    }

    fun saveFundGroups(groups: Map<String, FundGroup>) {
        mFundGroupsPer = GsonUtils.toJson(groups)
    }

    fun loadAllFunds(): List<FundBean> {
        val t = object : TypeToken<List<FundBean>>() {}.type
        return GsonUtils.fromJson(mAllFunds!!, t)
    }

    fun saveAllFunds(funds: List<FundBean>) {
        mAllFunds = GsonUtils.toJson(funds)
    }
}