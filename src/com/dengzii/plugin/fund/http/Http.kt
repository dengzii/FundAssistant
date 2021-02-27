package com.dengzii.plugin.fund.http

import org.apache.http.HttpResponse
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClientBuilder
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Http {

    private val client = HttpClientBuilder.create().build()

    companion object {

        private lateinit var instance: Http

        @JvmStatic
        fun getInstance(): Http {
            if (!this::instance.isInitialized) {
                instance = Http()
            }
            return instance
        }
    }

    @Throws(IOException::class)
    fun get(url: String): String {
        val r = RequestBuilder.get(url).build()
        val response: HttpResponse = client.execute(r)
        val inputStream = response.entity.content
        val builder = StringBuilder()
        val br = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            val s = br.readLine()
            if (s == null || s.isEmpty()) {
                break
            }
            builder.append(s)
        }
        return builder.toString()
    }

    @Throws(IOException::class)
    fun post(url: String, param: Map<String, String>): String {
        val requestBuilder = RequestBuilder.post(url)
        param.forEach { (name: String?, value: String?) -> requestBuilder.addParameter(name, value) }
        val response: HttpResponse = client.execute(requestBuilder.build())
        val inputStream = response.entity.content
        val builder = StringBuilder()
        val br = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            val s = br.readLine()
            if (s == null || s.isEmpty()) {
                break
            }
            builder.append(s)
        }
        return builder.toString()
    }

}