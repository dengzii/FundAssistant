package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.design.SettingsForm
import com.dengzii.plugin.fund.tools.ui.XDialog

class SettingsDialog : XDialog("设置") {

    private val form = SettingsForm()

    init {
        contentPane = form.contentPanel
        isModal = true
    }

    override fun onOpened() {
        super.onOpened()

    }
}