package com.dengzii.plugin.fund

import com.dengzii.plugin.fund.conf.FundColConfig
import com.dengzii.plugin.fund.conf.FundTheme
import com.dengzii.plugin.fund.design.SettingsForm
import com.intellij.openapi.options.SearchableConfigurable
import java.awt.Dimension
import javax.swing.JCheckBox
import javax.swing.JComponent

/**
 * @author https://github.com/dengzii/
 */
class PluginConfigurable : SearchableConfigurable {

    private lateinit var form: SettingsForm
    private val colCheckBox = mutableListOf<JCheckBox>()

    companion object {
        private var listener: (() -> Unit)? = null

        fun setConfigChangeListener(listener: (() -> Unit)?) {
            this.listener = listener
        }
    }

    override fun createComponent(): JComponent? {
        form = SettingsForm()
        colCheckBox.clear()

        form.comboBoxTheme.selectedIndex = FundTheme.values().indexOf(PluginConfig.fundTheme)
        form.comboBoxDuration.selectedIndex = PluginConfig.fundRefreshDuration / 30 - 1

        val defaultCol = FundColConfig()
        val colConfig = PluginConfig.fundColConfig
        FundColConfig.Col.values().forEach {
            val cb = JCheckBox(it.getName())
            cb.isSelected = colConfig.columns.contains(it)
            cb.isEnabled = defaultCol.columns.contains(it)
            colCheckBox.add(cb)
            form.panelColConf.add(cb)
        }
        val s = Dimension(form.contentPanel.width, form.contentPanel.height)
        form.panelColConf.preferredSize = s

        return form.contentPanel
    }

    override fun isModified(): Boolean {
        return true
    }

    override fun apply() {
        val enabledCol = mutableListOf<FundColConfig.Col>()
        colCheckBox.forEach {
            if (it.isSelected) {
                enabledCol.add(FundColConfig.Col.getByName(it.text))
            }
        }
        PluginConfig.fundColConfig = FundColConfig(enabledCol)
        PluginConfig.fundTheme = FundTheme.values()[form.comboBoxTheme.selectedIndex]
        PluginConfig.fundRefreshDuration = form.comboBoxDuration.selectedItem?.toString()?.toInt() ?: 20
        listener?.invoke()
    }

    override fun getDisplayName(): String {
        return "Fund Assistant"
    }

    override fun getId(): String {
        return "preferences.fund"
    }
}