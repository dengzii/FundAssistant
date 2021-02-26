package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.design.AddFundForm
import com.dengzii.plugin.fund.tools.ui.onClick
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class AddFundDialog : AddFundForm("添加基金") {

    init {
        isModal = true
    }

    override fun onOpened() {
        super.onOpened()
        buttonAdd.onClick {

        }
        buttonCancel.onClick {

        }
        textField.addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent?) {
                super.keyReleased(e)
                if (e?.keyCode == KeyEvent.VK_ENTER) {

                }
            }
        })
    }
}