package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.api.TianTianFundApi
import com.dengzii.plugin.fund.design.FundDetailForm
import com.dengzii.plugin.fund.utils.async
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import kotlin.random.Random

/**
 * @author https://github.com/dengzii/
 */
class FundDetailDialog : FundDetailForm() {

    private var pre = 4.5
    private val p1 = LineChart()

    init {
        persistDialogState = false
        container.add(p1, BorderLayout.CENTER)
        contentPane = contentPanel
        tfCount.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                super.keyPressed(e)
                if (e?.keyCode == KeyEvent.VK_ENTER) {
//                    random(50)

                    async {
                        TianTianFundApi().getNetValueHistory(tfCount.text, 1,30)
                    }.callback {
                        val ntv = it.map { b -> b.netValue.toDouble() }.asReversed()
                        println(ntv.size)
                        p1.setData(ntv)
                        p1.invalidate()
                        p1.repaint()
                    }
                }
            }
        })

    }

    private fun random(size: Int) {
        val data = mutableListOf<Double>()
        for (i in 1..size) {
            val rate = Random(System.nanoTime()).nextInt(-5, 5).toDouble() / 100.0
            pre += pre * rate
            data.add(pre)
        }
        p1.setData(data)
        p1.invalidate()
        p1.repaint()
        pre = 3.0
    }

    override fun onOpened() {
        super.onOpened()
        setBounds(400, 200, 900, 600)
    }
}