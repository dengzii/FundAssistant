package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.api.bean.NetValueBean
import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import java.awt.Component
import javax.swing.JLabel


class NetValueHistoryColInfo(name: String) : ColumnInfo<Any>(name) {

    override val columnClass = List::class.java

    override fun getRendererComponent(item: Any?, row: Int, col: Int): Component {
        val s = item as? List<*> ?: return JLabel("-")
        val chart = LineChart()
        chart.setData(s.filterIsInstance<NetValueBean>().map { it.netValue.toDouble() }.asReversed())
        return chart
    }
}