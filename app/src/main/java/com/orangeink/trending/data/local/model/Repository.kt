package com.orangeink.trending.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "trending_repositories", indices = [Index(value = ["name"])])
data class Repository(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("author") val author: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("url") val url: String,
    @SerializedName("description") val description: String,
    @SerializedName("language") val language: String?,
    @SerializedName("languageColor") val languageColor: String?,
    @SerializedName("stars") val stars: Int,
    @SerializedName("forks") val forks: Int,
    @SerializedName("currentPeriodStars") val currentPeriodStars: Int,
    @SerializedName("builtBy") val builtBy: List<BuiltBy>,
) {
    @Ignore
    var selected: Boolean = false
}

data class BuiltBy(
    @SerializedName("username") val username: String,
    @SerializedName("href") val href: String,
    @SerializedName("avatar") val avatar: String
)