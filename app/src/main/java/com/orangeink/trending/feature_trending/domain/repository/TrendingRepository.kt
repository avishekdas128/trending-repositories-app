package com.orangeink.trending.feature_trending.domain.repository

import com.orangeink.trending.core.util.Resource
import com.orangeink.trending.feature_trending.domain.model.Repository
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {

    fun getTrendingRepositories(): Flow<Resource<List<Repository>>>
}