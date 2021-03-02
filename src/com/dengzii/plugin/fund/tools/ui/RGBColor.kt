package com.dengzii.plugin.fund.tools.ui

import java.awt.Color

class RGBColor private constructor() {

    private var r: Int = 0
    private var g: Int = 0
    private var b: Int = 0

    companion object {
        fun get(c: String): RGBColor {
            var s = c.removePrefix("#")
            if (s.length != 6){
                s = "FFFFFF"
            }
            return RGBColor().apply {
                r = s.substring(0..1).conv()
                g = s.substring(2..3).conv()
                b = s.substring(4..5).conv()
            }
        }

        fun get(r: Int, g: Int, b: Int): RGBColor {
            return RGBColor().apply {
                this.r = r
                this.b = b
                this.g = g
            }
        }
    }

    fun getHSL(): Color {
        val hsb = FloatArray(3)
        Color.RGBtoHSB(r, g, b, hsb)
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2])
    }

    private fun String.conv(): Int {
        return Integer.parseInt(this, 16)
    }
}