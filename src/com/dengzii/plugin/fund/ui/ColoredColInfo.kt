package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import com.dengzii.plugin.fund.tools.ui.onClick
import com.intellij.ui.components.JBLabel
import java.awt.Color
import java.awt.Component
import java.awt.event.MouseEvent

class ColoredColInfo(
    private val name: String
) : ColumnInfo<Any>(name, true) {

    companion object {
        private val colorRed by lazy {
            val hsb = FloatArray(3)
            Color.RGBtoHSB(230, 120, 120, hsb)
            Color.getHSBColor(hsb[0], hsb[1], hsb[2])
        }
        private val colorGreen by lazy {
            val hsb = FloatArray(3)
            Color.RGBtoHSB(120, 230, 120, hsb)
            Color.getHSBColor(hsb[0], hsb[1], hsb[2])
        }
    }

    override val columnClass = String::class.java

    override fun getRendererComponent(item: Any?, row: Int, col: Int): Component {
        return item?.let { getComponent(it.toString()) } ?: super.getRendererComponent(item, row, col)
    }

    override fun getEditComponent(item: Any?, row: Int, col: Int): Component {
        return item?.let { getComponent(it.toString()) } ?: super.getRendererComponent(item, row, col)
    }

    private fun getComponent(text: String): Component {
        val s1 = text.replace("%", "").replace(",", "").toDouble()
        val color = if (s1 > 0) colorRed else colorGreen
        return JBLabel(text).apply {
            foreground = color
        }
    }
}
