package com.orangeink.trending.feature_trending.data.remote.dto

import com.orangeink.trending.feature_trending.data.local.entity.RepositoryEntity
import com.orangeink.trending.feature_trending.domain.model.BuiltBy

data class RepositoryDto(
    val author: String,
    val avatar: String,
    val builtBy: List<BuiltByDto>,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String?,
    val languageColor: String?,
    val name: String,
    val stars: Int,
    val url: String
) {
    fun toRepositoryEntity(): RepositoryEntity {
        return RepositoryEntity(
            author = author,
            builtBy = builtBy.map { it.toBuiltBy() },
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

data class BuiltByDto(
    val avatar: String,
    val href: String,
    val username: String
) {
    fun toBuiltBy(): BuiltBy {
        return BuiltBy(
            avatar = avatar
        )
    }
}