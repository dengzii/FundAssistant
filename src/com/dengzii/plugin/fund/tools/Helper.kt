package com.dengzii.plugin.fund.tools

import com.intellij.openapi.application.ApplicationManager

fun invokeLater(action: () -> Unit) {
    ApplicationManager.getApplication().invokeLater(action)
}