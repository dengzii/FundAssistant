package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.design.StockPanelForm
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory

class StockPanel : StockPanelForm(), ToolWindowPanel {

    override fun onCreate(project: Project, toolWindow: ToolWindow) {
        val factory = ContentFactory.SERVICE.getInstance()
        val content = factory.createContent(contentPanel, "Stock", false)
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

}