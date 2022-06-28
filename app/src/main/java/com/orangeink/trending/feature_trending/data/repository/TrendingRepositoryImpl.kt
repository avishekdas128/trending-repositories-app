package com.orangeink.trending.feature_trending.data.repository

import com.orangeink.trending.core.util.Resource
import com.orangeink.trending.feature_trending.data.local.TrendingDAO
import com.orangeink.trending.feature_trending.data.remote.TrendingService
import com.orangeink.trending.feature_trending.domain.model.Repository
import com.orangeink.trending.feature_trending.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val service: TrendingService,
    private val dao: TrendingDAO
) : TrendingRepository {

    override fun getTrendingRepositories(): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.Loading())

        val trendingRepositories = dao.getTrendingRepositories().map { it.toRepository() }
        emit(Resource.Loading(data = trendingRepositories))

        try {
            val remoteRepositories = service.getRepositories()
            dao.deleteAll()
            dao.insertAll(remoteRepositories.map { it.toRepositoryEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    data = trendingRepositories,
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    data = trendingRepositories,
                    message = "Couldn't reach server, check your internet connection!"
                )
            )
        }

        val newTrendingRepositories = dao.getTrendingRepositories().map { it.toRepository() }
        emit(Resource.Success(data = newTrendingRepositories))
    }
}