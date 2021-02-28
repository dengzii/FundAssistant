package com.dengzii.plugin.fund

import com.dengzii.plugin.fund.api.bean.FundBean
import com.dengzii.plugin.fund.conf.FundColConfig
import com.dengzii.plugin.fund.model.FundGroup
import com.dengzii.plugin.fund.tools.PersistentConfig
import com.dengzii.plugin.fund.utils.GsonUtils
import com.google.gson.reflect.TypeToken

object PluginConfig : PersistentConfig() {

    var fundColConfig by persistentProperty(FundColConfig(), "FundColConfig")
    var fundGroups by persistentProperty(mutableMapOf(Pair("default-group", FundGroup())), "FundGroups")
    private var mAllFunds by persistentProperty("[]", "AllFundList1")

    fun loadAllFunds(): List<FundBean> {
        val t = object : TypeToken<List<FundBean>>() {}.type
        return GsonUtils.fromJson(mAllFunds!!, t)
    }

    fun saveAllFunds(funds: List<FundBean>) {
        mAllFunds = GsonUtils.toJson(funds)
    }
}