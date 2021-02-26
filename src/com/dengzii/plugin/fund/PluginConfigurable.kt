package com.dengzii.plugin.fund

import com.dengzii.plugin.fund.design.SettingsForm
import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent

class PluginConfigurable : SearchableConfigurable {

    private lateinit var form: SettingsForm

    override fun createComponent(): JComponent? {
        form = SettingsForm()
        return form.contentPanel
    }

    override fun isModified(): Boolean {
        return false
    }

    override fun apply() {

    }

    override fun getDisplayName(): String {
        return "FundAssistant"
    }

    override fun getId(): String {
        return "preferences.fund"
    }
}