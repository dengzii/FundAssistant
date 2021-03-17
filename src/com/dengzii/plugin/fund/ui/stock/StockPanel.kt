package com.dengzii.plugin.fund.ui.stock

import com.dengzii.plugin.fund.PluginConfig
import com.dengzii.plugin.fund.api.AbstractPollTask
import com.dengzii.plugin.fund.api.SinaStockApi
import com.dengzii.plugin.fund.api.bean.StockUpdateBean
import com.dengzii.plugin.fund.conf.FundTheme
import com.dengzii.plugin.fund.conf.StockColConfig
import com.dengzii.plugin.fund.design.StockPanelForm
import com.dengzii.plugin.fund.model.UserStockModel
import com.dengzii.plugin.fund.tools.ui.ActionToolBarUtils
import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import com.dengzii.plugin.fund.tools.ui.TableAdapter
import com.dengzii.plugin.fund.ui.ToolWindowPanel
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionToolbarPosition
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.Icon
import javax.swing.JLabel
import javax.swing.SwingConstants
import javax.swing.table.TableRowSorter

/**
 * @author https://github.com/dengzii/
 */
class StockPanel : StockPanelForm(), ToolWindowPanel {

    private val tableData = mutableListOf<MutableList<Any?>>()
    private val columnInfos = mutableListOf<ColumnInfo<Any>>()
    private val tableAdapter = TableAdapter(tableData, columnInfos)
    private lateinit var stocks: MutableMap<String, UserStockModel>
    private lateinit var project: Project

    private lateinit var colConfig: List<StockColConfig.Col>
    private var pollTask: AbstractPollTask<List<StockUpdateBean>>? = null
    private val api = SinaStockApi()
    private var pollDuration = 20000L

    override fun onCreate(project: Project, toolWindow: ToolWindow) {
        val factory = ContentFactory.SERVICE.getInstance()
        val content = factory.createContent(contentPanel, "Stock", false)
        this.project = project
        init()
        toolWindow.contentManager.addContent(content)
    }

    private fun init() {

        stocks = PluginConfig.stockList.toMutableMap()

        tableStock.rowHeight = 30
        tableAdapter.setup(tableStock)

        val toolBar = ToolbarDecorator.createDecorator(tableStock)
            .addExtraActions(
                action("编辑", AllIcons.Actions.Edit) {
                    EditStockDialog.show(stocks) {
                        stocks.clear()
                        stocks.putAll(it)
                        PluginConfig.stockList = it
                        updateStockList()
                        updatePollTask()
                    }
                },
                action("立即刷新", AllIcons.Actions.Refresh) {
                    updatePollTask()
                },
//                action("设置", AllIcons.General.Settings) {
//                    ShowSettingsUtil.getInstance().editConfigurable(project, PluginConfigurable())
//                }
            )
            .setToolbarPosition(ActionToolbarPosition.LEFT)

        contentPanel.add(toolBar.createPanel(), BorderLayout.CENTER)

        initTableColumnInfo()
        initTableSorter()
        updatePollTask()
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
        colConfig = PluginConfig.stockColConfig.columns
        colConfig.forEach {
            when (PluginConfig.fundTheme) {
                FundTheme.Default -> it.getName()
                else -> it.name
            }
            val columnInfo = when (it) {
                StockColConfig.Col.StockCode,
                StockColConfig.Col.StockName,
                StockColConfig.Col.UpdateDate,
                StockColConfig.Col.UpdateTime -> {
                    AlignColInfo(it.getName(), SwingConstants.CENTER)
                }
                else -> {
                    AlignColInfo(it.getName(), SwingConstants.RIGHT)
                }
            }
            columnInfos.add(columnInfo)
        }
        tableAdapter.fireTableStructureChanged()
    }

    private fun updateStockList() {
        tableData.clear()
        stocks.forEach { s ->
            val col = mutableListOf<Any?>()
            colConfig.forEach {
                col.add(it.getAttr(s.value))
            }
            tableData.add(col)
        }
        tableAdapter.fireTableDataChanged()
    }

    private fun updatePollTask() {
        pollTask?.stop()
        pollTask?.unsubscribeAll()
        pollTask = api.subscribeUpdate(stocks.map { it.key })
        pollTask?.subscribe {
            it.forEach { s ->
                stocks[s.code]!!.update(s)
            }
            updateStockList()
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
                StockColConfig.Col.StockName,
                StockColConfig.Col.Trending,
                StockColConfig.Col.UpdateTime,
                StockColConfig.Col.UpdateDate -> {
                }
                else -> sorter.setComparator(index, numberComparator)
            }
        }
        tableStock.rowSorter = sorter
    }

    private fun action(hint: String, icon: Icon, block: (AnActionEvent) -> Unit) =
        ActionToolBarUtils.createActionButton(hint, icon, block).apply {
            contextComponent = contentPanel
        }

    private class AlignColInfo(name: String, val alignment: Int) : ColumnInfo<Any>(name, false) {
        override fun getRendererComponent(item: Any?, row: Int, col: Int): Component {
            val l = JLabel()
            l.horizontalAlignment = alignment
            l.text = item?.toString() ?: "-"
            return l
        }
    }
}