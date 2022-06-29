package com.orangeink.trending.feature_trending.data.util

import com.google.gson.Gson
import java.lang.reflect.Type

class GsonParser(
    private val gson: Gson
) : JSONParser {

    override fun <T> fromJSON(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJSON(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }
}