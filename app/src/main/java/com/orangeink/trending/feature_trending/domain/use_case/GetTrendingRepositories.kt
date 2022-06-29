package com.orangeink.trending.feature_trending.domain.use_case

import com.orangeink.trending.core.util.Resource
import com.orangeink.trending.feature_trending.domain.model.Repository
import kotlinx.coroutines.flow.Flow

interface GetTrendingRepositories {
    operator fun invoke(): Flow<Resource<List<Repository>>>
}