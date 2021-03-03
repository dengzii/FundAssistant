package com.dengzii.plugin.fund.http

import com.dengzii.plugin.fund.utils.Logger
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * @author https://github.com/dengzii/
 */
class Http {

    private lateinit var client: HttpClient

    companion object {

        private var instance: Http? = null

        @JvmStatic
        fun getInstance(): Http {
            if (instance == null) {
                instance = Http()
                val connectionManager = PoolingHttpClientConnectionManager(100, TimeUnit.SECONDS)
                connectionManager.maxTotal = 200
                connectionManager.defaultMaxPerRoute = 100
                val requestConfig =
                    RequestConfig.custom().setConnectionRequestTimeout(2000).setSocketTimeout(2000).build()
                instance!!.client =
                    HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
                        .build()
            }
            return instance!!
        }
    }

    @Throws(IOException::class, InterruptedException::class)
    fun get(url: String): String? {
        val get = HttpGet(url)

        val response: HttpResponse = client.execute(get)
        Logger.log("Http.get", "${response.statusLine.statusCode} $url")
        if (response.statusLine.statusCode != 200) {
            return null
        }
        val br = response.entity.content.bufferedReader(Charset.forName("utf-8"))
        val text = br.readText()
        get.releaseConnection()
        return text
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