package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import com.dengzii.plugin.fund.tools.ui.onClick
import com.intellij.ui.components.JBLabel
import java.awt.Color
import java.awt.Component
import java.awt.event.MouseEvent

/**
 * @author https://github.com/dengzii/
 */
class FundCodeColInfo(
        private val name: String = "Fund Code",
        private val onCellClick: (MouseEvent?) -> Unit
) : ColumnInfo<Any>(name, true) {

    override val columnClass = String::class.java

    override fun getRendererComponent(item: Any?, row: Int, col: Int): Component {
        return item?.let { getComponent(it.toString()) } ?: super.getRendererComponent(item, row, col)
    }

    override fun getEditComponent(item: Any?, row: Int, col: Int): Component {
        return item?.let { getComponent(it.toString()) } ?: super.getRendererComponent(item, row, col)
    }

    private fun getComponent(text: String): Component {
        return JBLabel(text).apply {
//            val hsb = FloatArray(3)
//            Color.RGBtoHSB(62, 143, 94, hsb)
//            foreground = Color.getHSBColor(hsb[0], hsb[1], hsb[2])
            onClick(onCellClick)
        }
    }
}
