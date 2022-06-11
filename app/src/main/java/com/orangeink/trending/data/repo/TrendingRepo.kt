package com.orangeink.trending.data.repo

import androidx.lifecycle.LiveData
import com.orangeink.trending.data.local.TrendingDAO
import com.orangeink.trending.data.local.model.Repository
import com.orangeink.trending.network.TrendingService
import com.orangeink.trending.util.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepo @Inject constructor(
    private val service: TrendingService,
    private val dao: TrendingDAO
) : BaseDataSource() {

    suspend fun remoteTrendingRepositories() = getResult {
        service.repositories()
    }

    suspend fun updateDataToLocal(data: List<Repository>) {
        dao.deleteAll()
        dao.insertAll(data)
    }

    fun getTrendingRepositories(): LiveData<List<Repository>> = dao.getTrendingRepositories()
}