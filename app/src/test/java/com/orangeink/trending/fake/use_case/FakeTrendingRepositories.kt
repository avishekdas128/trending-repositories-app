package com.orangeink.trending.fake.use_case

import com.orangeink.trending.core.util.Resource
import com.orangeink.trending.feature_trending.domain.model.Repository
import com.orangeink.trending.feature_trending.domain.use_case.GetTrendingRepositories
import com.orangeink.trending.util.repository1
import com.orangeink.trending.util.repository2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTrendingRepositories(
    private val isSuccessful: Boolean = true,
    private val emptyCache: Boolean = false
) : GetTrendingRepositories {

    var repository: List<Repository> = arrayListOf(repository1, repository2)

    override fun invoke(): Flow<Resource<List<Repository>>> {
        return flow {
            if (isSuccessful)
                emit(Resource.Success(data = repository))
            else {
                if (emptyCache)
                    emit(Resource.Error(message = "Error"))
                else
                    emit(
                        Resource.Error(
                            data = repository,
                            message = "Error"
                        )
                    )
            }
        }
    }
}