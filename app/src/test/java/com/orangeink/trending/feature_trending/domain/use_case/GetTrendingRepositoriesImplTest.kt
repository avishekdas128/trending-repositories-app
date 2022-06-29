package com.orangeink.trending.feature_trending.domain.use_case

import app.cash.turbine.test
import com.orangeink.trending.core.util.Resource
import com.orangeink.trending.feature_trending.domain.model.Repository
import com.orangeink.trending.feature_trending.domain.repository.TrendingRepository
import com.orangeink.trending.util.repository1
import com.orangeink.trending.util.repository2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetTrendingRepositoriesImplTest {

    private val mockRepository: TrendingRepository = mock()

    @Test
    fun `get trending repository returns repositories`() = runTest {
        val flowToReturn = flow<Resource<List<Repository>>> {
            emit(
                Resource.Success(
                    data = listOf(repository1, repository2)
                )
            )
        }
        val expectedList = arrayListOf(repository1, repository2)

        whenever(mockRepository.getTrendingRepositories()).doReturn(flowToReturn)
        val getTrendingRepositories = GetTrendingRepositoriesImpl(mockRepository)

        launch {
            getTrendingRepositories().test {
                with(awaitItem()) {
                    assertEquals(this.data, expectedList)
                }
                cancelAndIgnoreRemainingEvents()
            }
        }
        runCurrent()
    }

}