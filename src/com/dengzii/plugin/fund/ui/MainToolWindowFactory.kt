package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.PluginConfig
import com.dengzii.plugin.fund.api.TianTianFundApi
import com.dengzii.plugin.fund.api.bean.FundBean
import com.dengzii.plugin.fund.tools.NotificationUtils
import com.dengzii.plugin.fund.tools.invokeLater
import com.dengzii.plugin.fund.ui.fund.FundPanel
import com.dengzii.plugin.fund.ui.stock.StockPanel
import com.dengzii.plugin.fund.utils.async
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.jetbrains.rd.util.string.print

/**
 * @author https://github.com/dengzii/
 */
class MainToolWindowFactory : ToolWindowFactory {

    private val panels = mutableListOf<ToolWindowPanel>()

    override fun init(toolWindow: ToolWindow) {
        panels.forEach {
            it.onInit(toolWindow)
        }
    }

    override fun shouldBeAvailable(project: Project) = true

//    override fun isApplicable(project: Project) = true

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        if (panels.isEmpty()) {
            panels.add(FundPanel())
            panels.add(StockPanel())
        }
        async {
            try {
                TianTianFundApi().fundList
            } catch (e: Exception) {
                e.printStackTrace()
                invokeLater {
                    NotificationUtils.showError("请求基金列表接口出错了: ${e.message}", "Found Assistant")
                }
                emptyList<FundBean>()
            }
        }.callback {
            PluginConfig.allFund = it
        }

        toolWindow.activate {
            panels.forEach {
                it.onWindowActive()
            }
        }
        toolWindow.hide {
            panels.forEach {
                it.onWindowHide()
            }
        }
        toolWindow.show {
            panels.forEach {
                it.onWindowShow()
            }
        }
        panels.forEach {
            it.onCreate(project, toolWindow)
        }
    }
}