package com.dengzii.plugin.fund.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class MainToolWindowFactory : ToolWindowFactory {

    private val panels = mutableListOf<ToolWindowPanel>()

    override fun init(toolWindow: ToolWindow) {
        panels.forEach {
            it.onInit(toolWindow)
        }
    }

    override fun shouldBeAvailable(project: Project) = true

    override fun isApplicable(project: Project) = true

    override fun isDoNotActivateOnStart() = false

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        if (panels.isNotEmpty()) {
            return
        }
        panels.add(FundPanel())
        panels.add(StockPanel())

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