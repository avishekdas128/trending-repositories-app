package com.orangeink.trending.feature_trending.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.orangeink.trending.feature_trending.domain.model.BuiltBy
import com.orangeink.trending.feature_trending.domain.model.Repository

@Entity(tableName = "trending_repositories", indices = [Index(value = ["name"])])
data class RepositoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String,
    val name: String,
    val description: String,
    val language: String?,
    val languageColor: String?,
    val stars: Int,
    val forks: Int,
    val currentPeriodStars: Int,
    val builtBy: List<BuiltBy>,
) {
    fun toRepository(): Repository {
        return Repository(
            id = id,
            author = author,
            builtBy = builtBy,
            currentPeriodStars = currentPeriodStars,
            description = description,
            forks = forks,
            language = language,
            languageColor = languageColor,
            name = name,
            stars = stars
        )
    }
}