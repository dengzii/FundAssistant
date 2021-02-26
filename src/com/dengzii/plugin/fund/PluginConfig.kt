package com.dengzii.plugin.fund

import com.dengzii.plugin.fund.conf.FundColConfig
import com.dengzii.plugin.fund.model.FundGroup
import com.dengzii.plugin.fund.tools.PersistentConfig

object PluginConfig : PersistentConfig() {

    var fundColConfig by persistentProperty(FundColConfig(), "FundColConfig")
    var fundGroups by persistentProperty(mutableListOf<FundGroup>(), "FundGroups")
}