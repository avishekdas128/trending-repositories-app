package com.orangeink.trending.feature_trending.domain.use_case

import com.orangeink.trending.core.util.Resource
import com.orangeink.trending.feature_trending.domain.model.Repository
import com.orangeink.trending.feature_trending.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingRepositoriesImpl @Inject constructor(
    private val trendingRepository: TrendingRepository
) : GetTrendingRepositories {

    override fun invoke(): Flow<Resource<List<Repository>>> {
        return trendingRepository.getTrendingRepositories()
    }

}