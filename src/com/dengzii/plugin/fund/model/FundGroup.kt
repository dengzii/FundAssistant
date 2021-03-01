package com.dengzii.plugin.fund.model

import com.dengzii.plugin.fund.utils.GsonUtils
import com.google.gson.reflect.TypeToken

class FundGroup {

    var groupName = "default-group"
    var fundList = mutableMapOf<String, UserFundModel>()

    fun clone(): FundGroup {
        val t = object : TypeToken<FundGroup>() {}.type
        return GsonUtils.fromJson(GsonUtils.toJson(this), t)
    }
}