package com.orangeink.trending.feature_trending.data.util

import java.lang.reflect.Type

interface JSONParser {

    fun <T> fromJSON(json: String, type: Type): T?

    fun <T> toJSON(obj: T, type: Type): String?
}