package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.PluginConfig
import com.dengzii.plugin.fund.PluginConfigurable
import com.dengzii.plugin.fund.conf.FundColConfig
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

class FundPanel : FundPanelForm(), ToolWindowPanel {

    private lateinit var fundData: FundGroup
    private val tableData = mutableListOf<MutableList<Any?>>()
    private val columnInfos = mutableListOf<ColumnInfo<Any>>()
    private val tableAdapter = TableAdapter(tableData, columnInfos)

    private val colConfig = PluginConfig.fundColConfig!!.columns
    private lateinit var project: Project

    private fun init() {

        fundData = PluginConfig.fundGroups!!["default-group"]!!
        tableFund.rowHeight = 40
        tableFund.columnSelectionAllowed = false
        tableFund.rowSelectionAllowed = false
        tableAdapter.setup(tableFund)

        val toolBar = ToolbarDecorator.createDecorator(tableFund)
                .addExtraActions(ActionToolBarUtils.createActionButton("编辑", AllIcons.Actions.Edit) {
                    EditFundGroupListDialog.show(fundData) {
                        fundData = it
//                        PluginConfig.fundGroups!![it.groupName] = it
                        initTaleData()
                    }
                })
                .addExtraActions(ActionToolBarUtils.createActionButton("同步", AllIcons.Actions.Refresh) {

                })
                .addExtraActions(ActionToolBarUtils.createActionButton("设置", AllIcons.General.Settings) {
                    ShowSettingsUtil.getInstance().editConfigurable(project, PluginConfigurable())

                })
                .addExtraActions(ActionToolBarUtils.createActionButton("默认排序", AllIcons.ObjectBrowser.Sorted) {

                })
                .setToolbarPosition(ActionToolbarPosition.LEFT)

        initTableColumnInfo()
        initTaleData()
        initTableSorter()
        contentPanel.add(toolBar.createPanel(), BorderLayout.CENTER)
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

    }

    override fun onWindowShow() {

    }

    private fun initTableColumnInfo() {
        columnInfos.clear()
        colConfig.forEach {
            val columnInfo = when (it) {
                FundColConfig.Col.FundName -> {
                    FundCodeColInfo(it.getName()) {
                        // To fund detail.
                    }
                }
                FundColConfig.Col.NetValueReckon -> ColoredColInfo(it.getName())
                FundColConfig.Col.FloatingRange -> ColoredColInfo(it.getName())
                FundColConfig.Col.TotalYield -> ColoredColInfo(it.getName())
                FundColConfig.Col.TotalGains -> ColoredColInfo(it.getName())
                FundColConfig.Col.GainsReckon -> ColoredColInfo(it.getName())
                else -> ColumnInfo.new(it.getName(), false)
            }
            columnInfos.add(columnInfo)
        }
        tableAdapter.fireTableStructureChanged()
    }

    private fun initTaleData() {
        tableData.clear()
        fundData.fundList.forEach { (_, m) ->
            val row = mutableListOf<Any?>()
            colConfig.forEach {
                row.add(it.getAttr(m))
            }
            tableData.add(row)
        }
        tableAdapter.fireTableDataChanged()
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
                FundColConfig.Col.FloatingRange -> sorter.setComparator(index, numberComparator)
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