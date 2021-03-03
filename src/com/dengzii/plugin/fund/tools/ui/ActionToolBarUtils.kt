package com.dengzii.plugin.fund.tools.ui

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CheckedActionGroup
import com.intellij.openapi.actionSystem.impl.ActionButton
import com.intellij.openapi.actionSystem.impl.PresentationFactory
import com.intellij.ui.AnActionButton
import com.intellij.ui.AnActionButton.AnActionButtonWrapper
import com.intellij.ui.AnActionButton.CheckedAnActionButton
import com.intellij.util.ui.JBUI
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.JPanel

object ActionToolBarUtils {

    fun create(place: String, action: List<Action>): JComponent {

        val panel = JPanel()
        panel.layout = FlowLayout().also {
            it.alignment = FlowLayout.LEFT
            it.hgap = 0
            it.vgap = 0
        }
        val factory = PresentationFactory()
        action.forEach {
            val presentation = factory.getPresentation(it).also { p ->
                p.icon = it.icon
                p.isEnabled = it.isEnabled
                p.description = it.desc
            }
            val bt = ActionButton(it, presentation, place, Dimension(22, 24))
            bt.setIconInsets(JBUI.insets(0))
            panel.add(bt)
        }
        return panel
    }

    fun createActionButton(hint: String, icon: Icon, block: (AnActionEvent) -> Unit): AnActionButton {
        val action = object:AnAction(){
            override fun actionPerformed(p0: AnActionEvent) {
                block(p0)
            }
        }
        val presentation = action.templatePresentation
        presentation.icon = icon
        presentation.description = hint
        presentation.text = hint
        val button = AnActionButtonWrapper(presentation, action)
        button.shortcut = action.shortcutSet
        return button
    }

    class Action(
        var icon: Icon,
        var isEnabled: Boolean = true,
        var desc: String = "",
        var action: () -> Unit
    ) : AnAction() {

        override fun actionPerformed(p0: AnActionEvent) {
            action.invoke()
        }
    }
}