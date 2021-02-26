package com.dengzii.plugin.fund.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow

interface ToolWindowPanel {
    fun onCreate(project: Project, toolWindow: ToolWindow)
    fun onInit(toolWindow: ToolWindow)
    fun onWindowActive()
    fun onWindowHide()
    fun onWindowShow()
}