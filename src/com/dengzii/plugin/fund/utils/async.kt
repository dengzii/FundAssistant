package com.dengzii.plugin.fund.utils

import com.dengzii.plugin.fund.tools.invokeLater
import java.util.concurrent.Executors


private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2)

fun <T> async(block: () -> T): AsyncTask<T> {
    return AsyncTask(block)
}

class AsyncTask<T>(private val task: () -> T) : Runnable {

    private lateinit var callback: (T) -> Unit

    override fun run() {
        val result = task()
        invokeLater {
            callback(result)
        }
    }

    fun callback(callback: (T) -> Unit) {
        this.callback = callback
        executor.submit(this)
    }
}
