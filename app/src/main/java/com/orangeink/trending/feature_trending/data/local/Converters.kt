package com.orangeink.trending.feature_trending.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.orangeink.trending.feature_trending.data.util.JSONParser
import com.orangeink.trending.feature_trending.domain.model.BuiltBy

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JSONParser
) {

    @TypeConverter
    fun fromBuiltByJSON(json: String): List<BuiltBy> {
        return jsonParser.fromJSON<List<BuiltBy>>(
            json,
            object : TypeToken<List<BuiltBy>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toBuiltByJSON(builtByList: List<BuiltBy>): String {
        return jsonParser.toJSON(
            builtByList,
            object : TypeToken<List<BuiltBy>>() {}.type
        ) ?: "[]"
    }
}