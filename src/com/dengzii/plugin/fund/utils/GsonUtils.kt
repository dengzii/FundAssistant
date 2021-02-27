package com.dengzii.plugin.fund.utils

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * @author https://github.com/dengzii
 */
object GsonUtils {
    @JvmStatic
    private val gson = Gson()

    @JvmStatic
    fun <T> fromJson(json: String, type: Type): T {
        return gson.fromJson<T>(json, type)
    }
}