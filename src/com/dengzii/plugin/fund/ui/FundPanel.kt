package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.PluginConfig
import com.dengzii.plugin.fund.PluginConfigurable
import com.dengzii.plugin.fund.api.AbstractPollTask
import com.dengzii.plugin.fund.api.TianTianFundApi
import com.dengzii.plugin.fund.api.bean.FundBean
import com.dengzii.plugin.fund.conf.FundColConfig
import com.dengzii.plugin.fund.conf.FundTheme
import com.dengzii.plugin.fund.design.FundPanelForm
import com.dengzii.plugin.fund.model.FundGroup
import com.dengzii.plugin.fund.tools.ui.ActionToolBarUtils
import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import com.dengzii.plugin.fund.tools.ui.TableAdapter
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionToolbarPosition
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import javax.swing.table.TableRowSorter

/**
 * @author https://github.com/dengzii/
 */
class FundPanel : FundPanelForm(), ToolWindowPanel {

    private lateinit var fundData: FundGroup
    private val tableData = mutableListOf<MutableList<Any?>>()
    private val columnInfos = mutableListOf<ColumnInfo<Any>>()
    private val tableAdapter = TableAdapter(tableData, columnInfos)

    private val funds = mutableListOf<FundBean>()
    private var pollDuration = 20000L
    private val api = TianTianFundApi()
    private var pollTask: AbstractPollTask<List<FundBean>>? = null
    private var colConfig = PluginConfig.fundColConfig!!.columns
    private lateinit var project: Project

    private fun init() {
        pollDuration = PluginConfig.fundRefreshDuration!! * 1000L

        fundData = PluginConfig.loadFundGroups().getOrElse("default-group") { FundGroup() }

        tableFund.rowHeight = 40
        tableFund.columnSelectionAllowed = false
        tableFund.rowSelectionAllowed = false
        tableAdapter.setup(tableFund)

        val toolBar = ToolbarDecorator.createDecorator(tableFund)
            .addExtraActions(ActionToolBarUtils.createActionButton("编辑", AllIcons.Actions.Edit) {
                EditFundGroupListDialog.show(fundData) {
                    fundData = it
                    PluginConfig.saveFundGroups(mutableMapOf(Pair(fundData.groupName, fundData)))
                    updateFundList()
                    updatePollTask()
                }
            })
            .addExtraActions(ActionToolBarUtils.createActionButton("立即刷新", AllIcons.Actions.Refresh) {
                updatePollTask()
            })
            .addExtraActions(ActionToolBarUtils.createActionButton("设置", AllIcons.General.Settings) {
                ShowSettingsUtil.getInstance().editConfigurable(project, PluginConfigurable())

            })
            .setToolbarPosition(ActionToolbarPosition.LEFT)

        initTableColumnInfo()
        updateFundList()
        updatePollTask()
        initTableSorter()
        contentPanel.add(toolBar.createPanel(), BorderLayout.CENTER)

        PluginConfigurable.setConfigChangeListener {
            initTableColumnInfo()
            updateFundList()
        }
    }

    override fun onCreate(project: Project, toolWindow: ToolWindow) {
        if (!this::project.isInitialized) {
            this.project = project
        }
        val factory = ContentFactory.SERVICE.getInstance()
        val content = factory.createContent(contentPanel, "Fund", false)
        init()
        toolWindow.contentManager.addContent(content)
    }

    override fun onInit(toolWindow: ToolWindow) {

    }

    override fun onWindowActive() {

    }

    override fun onWindowHide() {
//        pollTask?.stop()
    }

    override fun onWindowShow() {
//        pollTask?.start(pollDuration)
    }

    private fun initTableColumnInfo() {
        columnInfos.clear()
        colConfig = PluginConfig.fundColConfig!!.columns
        colConfig.forEach {
            when (PluginConfig.fundTheme!!) {
                FundTheme.Default -> it.getName()
                else -> it.name
            }
            val n = it.getName()
            val columnInfo = when (it) {
                FundColConfig.Col.FundName -> {
                    FundCodeColInfo(n) {
                        // To fund detail.
                    }
                }
                FundColConfig.Col.GrowthRateReckon -> ColoredColInfo(n)
                FundColConfig.Col.TotalYield -> ColoredColInfo(n)
                FundColConfig.Col.TotalGains -> ColoredColInfo(n)
                FundColConfig.Col.GainsReckon -> ColoredColInfo(n)
                else -> ColumnInfo.new(n, false)
            }
            columnInfos.add(columnInfo)
        }
        tableAdapter.fireTableStructureChanged()
    }

    private fun updateFundList() {
        tableData.clear()
        funds.clear()
        fundData.fundList.forEach { (_, m) ->
            val col = mutableListOf<Any?>()
            funds.add(m.fundBean)
            colConfig.forEach {
                col.add(it.getAttr(m))
            }
            tableData.add(col)
        }
        tableAdapter.fireTableDataChanged()
    }

    private fun updatePollTask() {
        pollTask?.stop()
        pollTask?.unsubscribeAll()
        pollTask = api.updateFundList(fundData.fundList.map { it.value.fundBean })
        pollTask?.subscribe {
            it.forEach { f ->
                fundData.fundList[f.fundCode]?.updateFund(f)
            }
            updateFundList()
        }
        pollTask?.start(pollDuration)
    }

    private fun initTableSorter() {
        val sorter = TableRowSorter(tableAdapter)
        val numberComparator = Comparator<String> { o1, o2 ->
            val s1 = o1.replace("%", "").replace(",", "").toDouble()
            val s2 = o2.replace("%", "").replace(",", "").toDouble()
            s1.compareTo(s2)
        }
        colConfig.forEachIndexed { index, col ->
            when (col) {
                FundColConfig.Col.FundName -> {
                    sorter.setComparator(index, Comparator<String> { s1, s2 ->
                        s1.compareTo(s2)
                    })
                }
                FundColConfig.Col.NetValueReckon -> sorter.setComparator(index, numberComparator)
                FundColConfig.Col.GrowthRateReckon -> sorter.setComparator(index, numberComparator)
                FundColConfig.Col.BuyingPrice -> sorter.setComparator(index, numberComparator)
                FundColConfig.Col.TotalYield -> sorter.setComparator(index, numberComparator)
                FundColConfig.Col.TotalGains -> sorter.setComparator(index, numberComparator)
                FundColConfig.Col.HoldingShare -> sorter.setComparator(index, numberComparator)
                FundColConfig.Col.GainsReckon -> sorter.setComparator(index, numberComparator)
                else -> {
                }
            }
        }
        tableFund.rowSorter = sorter
    }
}