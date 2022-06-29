package com.orangeink.trending.feature_trending.presentation

import com.orangeink.trending.feature_trending.domain.model.Repository

data class TrendingRepositoryState(
    val trendingRepositories: List<Repository> = emptyList(),
    val isLoading: Boolean = true,
    val noNetwork: Boolean = false
)