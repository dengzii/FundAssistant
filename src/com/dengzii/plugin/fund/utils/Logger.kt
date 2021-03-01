package com.dengzii.plugin.fund.utils

object Logger {

    @JvmStatic
    fun log(tag: String = "", log: String) {
        println("$tag: $log")
    }
}