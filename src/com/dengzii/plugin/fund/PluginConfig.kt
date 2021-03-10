package com.dengzii.plugin.fund

import com.dengzii.plugin.fund.api.bean.FundBean
import com.dengzii.plugin.fund.conf.FundColConfig
import com.dengzii.plugin.fund.conf.FundTheme
import com.dengzii.plugin.fund.conf.StockColConfig
import com.dengzii.plugin.fund.model.FundGroup
import com.dengzii.plugin.fund.tools.PersistentConfig
import com.dengzii.plugin.fund.utils.GsonUtils
import com.google.gson.reflect.TypeToken
import com.intellij.ide.util.PropertiesComponent
import java.lang.reflect.Type

/**
 * @author https://github.com/dengzii/
 */
object PluginConfig : PersistentConfig() {

    var stockColConfig
        get() = load("StockColConfig", StockColConfig(), object : TypeToken<StockColConfig>() {}.type)
        set(value) = save("StockColConfig", value)

    var fundColConfig
        get() = load("FundColConfig", FundColConfig(), object : TypeToken<FundColConfig>() {}.type)
        set(value) = save("FundColConfig", value)

    var fundTheme
        get() = load("FundTheme", FundTheme.Default, object : TypeToken<FundTheme>() {}.type)
        set(value) = save("FundTheme", value)

    var fundRefreshDuration: Int
        get() = PropertiesComponent.getInstance().getValue("FundRefreshDuration", "60").toInt()
        set(value) = PropertiesComponent.getInstance().setValue("FundRefreshDuration", value.toString())


    var fundGroup: Map<String, FundGroup>
        get() = load("FundGroups", mapOf(), object : TypeToken<Map<String, FundGroup>>() {}.type)
        set(value) = save("FundGroups", value)

    var allFund: List<FundBean>
        get() = load("AllFunds", listOf(), object : TypeToken<List<FundBean>>() {}.type)
        set(value) = save("AllFunds", value)


    private fun <T> load(key: String, default: T, type: Type): T {
        val v = PropertiesComponent.getInstance().getValue(key)
        if (v.isNullOrBlank()) {
            return default
        }
        return try {
            GsonUtils.fromJson(v, type)
        } catch (e: Throwable) {
            PropertiesComponent.getInstance().unsetValue(key)
            default
        }
    }

    private fun save(key: String, value: Any) {
        PropertiesComponent.getInstance().setValue(key, GsonUtils.toJson(value))
    }
}