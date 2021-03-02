package com.dengzii.plugin.fund.utils

/**
 * @author https://github.com/dengzii/
 */
object Logger {

    @JvmStatic
    fun log(tag: String = "", log: String) {
        println("$tag: $log")
    }
}