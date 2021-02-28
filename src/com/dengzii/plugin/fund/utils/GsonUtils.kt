package com.dengzii.plugin.fund.utils

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

/**
 * @author https://github.com/dengzii
 */
object GsonUtils {
    @JvmStatic
    private val gson = GsonBuilder().serializeNulls().setLenient().create()

    @JvmStatic
    fun <T> fromJson(json: String, type: Type): T {
        return gson.fromJson<T>(json, type)
    }

    fun toJson(t: Any): String {
        return gson.toJson(t)
    }
}