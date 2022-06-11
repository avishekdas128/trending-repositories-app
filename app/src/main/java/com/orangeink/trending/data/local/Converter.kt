package com.orangeink.trending.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orangeink.trending.data.local.model.BuiltBy

object Converter {

    private lateinit var gson: Gson

    fun initialize(gson: Gson) {
        this.gson = gson
    }

    @TypeConverter
    fun fromBuiltByList(value: List<BuiltBy>): String {
        val type = object : TypeToken<List<BuiltBy>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toBuiltByList(value: String): List<BuiltBy> {
        val type = object : TypeToken<List<BuiltBy>>() {}.type
        return gson.fromJson(value, type)
    }
}