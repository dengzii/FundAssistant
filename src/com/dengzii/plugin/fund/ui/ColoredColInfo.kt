package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.PluginConfig
import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import com.dengzii.plugin.fund.tools.ui.RGBColor
import com.intellij.ui.components.JBLabel
import java.awt.Component

/**
 * @author https://github.com/dengzii/
 */
class ColoredColInfo(
    private val name: String
) : ColumnInfo<Any>(name, true) {


    private val colorRise by lazy {
        RGBColor.get(PluginConfig.fundTheme!!.rise).getHSL()
    }
    private val colorFall by lazy {
        RGBColor.get(PluginConfig.fundTheme!!.fall).getHSL()
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
        val color = if (s1 > 0) colorRise else colorFall
        return JBLabel(text).apply {
            foreground = color
        }
    }
}
