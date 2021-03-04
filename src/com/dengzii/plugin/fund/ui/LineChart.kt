package com.dengzii.plugin.fund.ui

import java.awt.Graphics
import java.awt.Polygon
import java.text.DecimalFormat
import javax.swing.JPanel
import kotlin.math.roundToInt

/**
 * @author https://github.com/dengzii/
 */
open class LineChart : JPanel() {

    private var minValue = 1.0
    private var maxValue = 1.0

    private var valueDiff = 0.0

    private var showLabel = true

    private val labelWith = 26
    private val labelHeight = 20

    private val data = mutableListOf<Double>()

    fun setData(data: List<Double>) {
        this.data.clear()
        this.data.addAll(data)
        if (data.isEmpty()) {
            return
        }
        val sorted = data.sortedBy { it }
        minValue = sorted.first()
        maxValue = sorted.last()
        val ofs = (maxValue - minValue) / getScaleCountY()
        valueDiff = maxValue + ofs - minValue
    }

    override fun invalidate() {
        super.invalidate()
        showLabel = height > 200 && width > 300
        val diff = maxValue - minValue
        valueDiff = when {
            height > 200 -> diff + (diff) / getScaleCountY()
            else -> maxValue - minValue
        }
    }

    private fun getLabelWidth(): Int {
        return if (showLabel) labelWith else 0
    }

    private fun getLabelHeight(): Int {
        return if (showLabel) labelHeight else 0
    }

    private fun polygonMode(): Boolean {
        return height < 200 || width < 300
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        if (getDateCount() == 0) {
            return
        }
        g.color = foreground
        // Translate coordinate to left bottom
        g.translate(getLabelWidth(), height - getLabelHeight())

        val scaleY = (height - getLabelHeight()) / getScaleCountY() + 0.0
        val scaleYValue = valueDiff / getScaleCountY()
        val scaleX = (width - getLabelWidth()) / getScaleCuntX().toDouble()

        drawScaleX(g, scaleX)
        drawScaleY(g, scaleY, scaleYValue)

        // draw data line
        var preY = 0.0
        val p = Polygon()
        for (i in 0 until getDateCount()) {
            val value = getValueAt(i)
            val y = ((value - minValue) / scaleYValue) * -scaleY
            if (i == 0) {
                if (polygonMode()) {
                    p.addPoint(0, y.roundToInt())
                }
                preY = y
                continue
            }
            val x1 = scaleX * (i - 1)
            val y1 = preY
            val x2 = scaleX * i
            if (showLabel) {
                g.drawString(getValueLabelAt(i), x2, y)
            }
            if (polygonMode()) {
                p.addPoint(x2.roundToInt(), y.roundToInt())
            } else {
                g.drawLine(x1, y1, x2, y)
            }
            preY = y
        }
        if (polygonMode()) {
            p.addPoint((scaleX * getDateCount()).roundToInt(), 0)
            p.addPoint(0, 0)
            p.addPoint(0, (((getValueAt(0) - minValue) / scaleYValue) * -scaleY).roundToInt())
            g.fillPolygon(p)
        }
    }

    open fun getDateCount(): Int {
        return data.size
    }

    open fun getValueAt(index: Int): Double {
        return data[index]
    }

    open fun getValueLabelAt(index: Int): String {
        return data[index].formatStr()
    }

    open fun getScaleLabelY(scaleIndex: Int, scaleValue: Double): String {
        return scaleValue.formatStr()
    }

    open fun getScaleLabelX(scaleIndex: Int): String {
        return scaleIndex.toString()
    }

    open fun drawScaleY(g: Graphics, scaleSize: Double, scaleValue: Double) {
        g.drawLine(0.0, 0.0, 0.0, -(getScaleCountY() - 1) * scaleSize)
        if (showLabel) {
            // draw scale string
            for (i in 0 until getScaleCountY()) {
                val y = i * -scaleSize
                g.drawLine(0.0, y, 3.0, y)
                val s = (scaleValue * i + minValue)
                g.drawString(getScaleLabelY(i, s), -labelWith.toDouble(), y)
            }
        }
    }

    open fun drawScaleX(g: Graphics, scaleSize: Double) {
        g.drawLine(0.0, 0.0, width - getLabelWidth() - scaleSize, 0.0)
        for (i in 0 until getScaleCuntX()) {
            if (showLabel) {
                g.drawLine(scaleSize * i, 0.0, scaleSize * i, -3.0)
                g.drawString(getScaleLabelX(i), scaleSize * i - 5, 10.0)
            }
        }
    }

    private fun getScaleCountY() = when {
        height > 300 -> 12
        height > 200 -> 12
        height > 100 -> 12
        else -> 12
    }

    private fun getScaleCuntX(): Int {
        return if (height > 200) data.size else data.size - 1
    }

    private fun Graphics.drawLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        drawLine(x1.roundToInt(), y1.roundToInt(), x2.roundToInt(), y2.roundToInt())
    }

    private fun Graphics.drawString(str: String, x: Double, y: Double) {
        drawString(str, x.roundToInt(), y.roundToInt())
    }

    private val f = DecimalFormat("##.##")
    private fun Double.formatStr(): String {
        return f.format(this)
    }
}
