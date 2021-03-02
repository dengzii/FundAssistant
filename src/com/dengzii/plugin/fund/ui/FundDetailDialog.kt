package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.conf.FundTheme
import com.dengzii.plugin.fund.design.FundDetailForm
import com.dengzii.plugin.fund.tools.ui.RGBColor
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.text.DecimalFormat
import javax.swing.JPanel
import kotlin.random.Random

/**
 * @author https://github.com/dengzii/
 */
class FundDetailDialog : FundDetailForm() {

    private var pre = 200f
    private val p1 = P1(mutableListOf())

    init {
        persistDialogState = false
        contentPanel.add(p1, BorderLayout.CENTER)
        contentPane = contentPanel
        textField1.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                super.keyPressed(e)
                if (e?.keyCode == KeyEvent.VK_ENTER) {
                    p1.data = random(textField1.text.toInt())
                    p1.repaint()
                    p1.init()
                    pre = 3f
                }
            }
        })
    }

    private fun random(size: Int): List<Float> {
        val data = mutableListOf<Float>()
        for (i in 1..size) {
            val rate = Random(System.nanoTime()).nextInt(-5, 5).toFloat() / 100f
            pre += pre * rate
            data.add(pre)
        }
        return data
    }

    override fun onOpened() {
        super.onOpened()
        setBounds(400, 200, 900, 600)
    }

    class P1(var data: List<Float>) : JPanel() {

        private val dY = 300
        private val dX = 850

        private var min = 1f
        private var max = 1f

        private var m = 0f
        private val yOffset = 0.02f

        init {
            setBounds(0, 0, dX + 100, dY + 100)
            background = Color.BLACK
        }

        fun init() {
            val sorted = data.sortedBy { it }
            min = sorted.first() - yOffset
            max = sorted.last() - yOffset
            m = max - min
        }

        override fun paint(g: Graphics) {
            super.paint(g)
            if (data.isEmpty()) {
                return
            }
            g.color = RGBColor.get(FundTheme.Default.fall).getHSL()
            g.translate(50, dY + 50)

            g.drawLine(0, 0, 0, -dY - 10)
            g.drawLine(0, 0, dX + 10, 0)


            val sY = dY / 10
            val sYY = m / 10
            val sX = dX / data.size

            var preY = 0f

            for (i in data.indices) {
                val v = data[i]

                g.drawLine(sX * i, 0, sX * i, -3)
                g.drawString(i.toString(), sX * i - 5, 10)

                val y = ((v - min) / sYY) * -sY
                if (i == 0) {
                    preY = y
                    continue
                }

                val x1 = sX * (i - 1)
                val y1 = preY.toInt()
                val x2 = sX * i
                val y2 = y.toInt()
                g.drawString(v.formatStr(), x2, y2)
                g.drawLine(x1, y1, x2, y2)
                preY = y
            }

            for (i in 0..10) {
                val y = i * -sY
                g.drawLine(0, y, 3, y)
                val s = (sYY * i + min - yOffset).formatStr()
                g.drawString(s, -35, y)
            }
        }

        private val f = DecimalFormat("##.##")
        private fun Float.formatStr(): String {
            return f.format(this)
        }
    }


}