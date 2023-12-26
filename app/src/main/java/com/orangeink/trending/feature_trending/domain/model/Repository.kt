package com.orangeink.trending.feature_trending.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Repository(
    val id: Int,
    val author: String,
    val builtBy: List<BuiltBy>,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String?,
    val languageColor: String?,
    val name: String,
    val stars: Int
)

data class BuiltBy(
    val avatar: String
)

