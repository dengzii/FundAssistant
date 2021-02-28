package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.PluginConfig
import com.dengzii.plugin.fund.api.TianTianFundApi
import com.dengzii.plugin.fund.api.bean.FundBean
import com.dengzii.plugin.fund.design.EditFundGroupForm
import com.dengzii.plugin.fund.model.FundGroup
import com.dengzii.plugin.fund.model.UserFundModel
import com.dengzii.plugin.fund.tools.ui.onClick
import com.dengzii.plugin.fund.utils.async
import java.awt.Dimension
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.DefaultListModel
import javax.swing.ListSelectionModel

class EditFundGroupListDialog(
        private val allFunds: List<FundBean>,
        private val fundGroup: FundGroup,
        private val callback: (callback: FundGroup) -> Unit
) : EditFundGroupForm("编辑基金列表") {

    private val searchListModel = DefaultListModel<String>()
    private val selectedListModel = DefaultListModel<String>()
    private var listDither1 = 0L
    private var listDither2 = 0L

    init {
        isModal = true
    }

    companion object {
        fun show(origin: FundGroup, callback: (callback: FundGroup) -> Unit) {
            val allFunds = PluginConfig.loadAllFunds()
            if (allFunds.isNullOrEmpty()) {
                async {
                    TianTianFundApi().fundList
                }.callback {
                    PluginConfig.saveAllFunds(it)
                    EditFundGroupListDialog(it, origin, callback).packAndShow()
                }
            } else {
                EditFundGroupListDialog(allFunds, origin, callback).packAndShow()
            }
        }
    }

    override fun onOpened() {
        super.onOpened()
        buttonApply.onClick {
            isVisible = false
            callback(fundGroup)
            dispose()
        }
        buttonCancel.onClick {
            hideAndDispose()
        }
        textFieldSearch.addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent?) {
                super.keyReleased(e)
                val text = textFieldSearch.text.trim()
                if (text.isBlank()) {
                    return
                }
                searchListModel.clear()
                allFunds.forEach {
                    if (it.fundName.contains(text) || it.fundCode.contains(text) ||
                            it.pingYingAbbr.contains(text) || it.pingYing.contains(text)) {
                        searchListModel.addElement("${it.fundName}-${it.fundCode}")
                    }
                }
            }
        })
        listSearch.model = searchListModel
        listSelected.model = selectedListModel

        listSearch.selectionMode = ListSelectionModel.SINGLE_SELECTION
        listSelected.selectionMode = ListSelectionModel.SINGLE_SELECTION

        listSearch.addListSelectionListener {
            if (System.currentTimeMillis() - listDither1 < 300) {
                return@addListSelectionListener
            }
            val selectedIndex = listSearch.selectedIndex
            if (selectedIndex == -1) {
                return@addListSelectionListener
            }
            listDither1 = System.currentTimeMillis()
            val selected = allFunds[selectedIndex]
            if (fundGroup.fundList.containsKey(selected.fundCode)) {
                return@addListSelectionListener
            }
            fundGroup.fundList[selected.fundCode] = UserFundModel(selected)
            selectedListModel.addElement("${selected.fundName}-${selected.fundCode}")
        }
        listSelected.addListSelectionListener {
            if (System.currentTimeMillis() - listDither2 < 300) {
                return@addListSelectionListener
            }
            val selectedIndex = listSelected.selectedIndex
            if (selectedIndex == -1) {
                return@addListSelectionListener
            }
            listDither2 = System.currentTimeMillis()
            val code = selectedListModel.get(selectedIndex).split("-")[1]
            fundGroup.fundList.remove(code)
            selectedListModel.remove(selectedIndex)
        }

        listSearch.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                super.mouseClicked(e)
                if (e.button != MouseEvent.BUTTON3) {
                    return
                }
            }
        })

        fundGroup.fundList.forEach { (_, u) ->
            selectedListModel.addElement("${u.fundBean.fundName}-${u.fundBean.fundCode}")
        }
    }

    override fun getDefaultSize(): Dimension {
        return Dimension(900, 700)
    }
}