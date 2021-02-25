package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.design.MainWindowForm
import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import com.dengzii.plugin.fund.tools.ui.TableAdapter
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionToolbarPosition
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.AnActionButton
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout

class MainWindow : MainWindowForm(), ToolWindowFactory {

    private val fundData = mutableListOf<MutableList<Any?>>()


    override fun init(toolWindow: ToolWindow) {
        println("MainWindow.init")

        val columnInfo = ColumnInfo.of("估算净值", "涨跌幅", "更新时间").toMutableList()
        columnInfo.add(0, ColumnFundCode {

        })

        val adapter = TableAdapter(fundData, columnInfo)
        adapter.setup(tableFund)
        tableFund.rowHeight = 40
        tableFund.columnSelectionAllowed = false
        tableFund.rowSelectionAllowed = false

        fundData.add(mutableListOf("A1", "B1", "C1", "D1"))
        fundData.add(mutableListOf("A2", "B2", "C2", "D2"))
        fundData.add(mutableListOf("A3", "B3", "C3", "D3"))

        adapter.fireTableDataChanged()
        adapter.fireTableStructureChanged()

        val p = ToolbarDecorator.createDecorator(tableFund)
                .addExtraActions(object : AnActionButton("--", AllIcons.Actions.Refresh) {
                    override fun actionPerformed(p0: AnActionEvent) {

                    }
                })
                .addExtraActions(object : AnActionButton("--", AllIcons.Actions.StopRefresh) {
                    override fun actionPerformed(p0: AnActionEvent) {

                    }
                })
                .setToolbarPosition(ActionToolbarPosition.TOP)
                .createPanel()

        contentPanel.add(p, BorderLayout.CENTER)
    }

    override fun shouldBeAvailable(project: Project) = true

    override fun isApplicable(project: Project) = true

    override fun isDoNotActivateOnStart() = false

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        println("MainWindow.createToolWindowContent")

        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(contentPanel, "Fund", false)

        toolWindow.contentManager.addContent(content)
    }
}