package com.orangeink.trending.feature_trending.presentation

import com.orangeink.trending.feature_trending.data.local.TrendingDAO
import com.orangeink.trending.feature_trending.data.remote.TrendingService
import com.orangeink.trending.feature_trending.data.repository.TrendingRepositoryImpl
import com.orangeink.trending.feature_trending.domain.use_case.GetTrendingRepositoriesImpl
import com.orangeink.trending.util.CoroutineRule
import com.orangeink.trending.util.responseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class TrendingViewModelIntegrationTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val mockService: TrendingService = mock()
    private val mockTrendingDAO: TrendingDAO = mock()

    private val trendingRepository = TrendingRepositoryImpl(mockService, mockTrendingDAO)

    private val getTrendingRepositories = GetTrendingRepositoriesImpl(trendingRepository)

    @Test
    fun `calling loadData() triggers the api client, dao delete and dao insertAll`() = runTest {
        whenever(mockService.getRepositories()).doReturn(
            Response.success(arrayListOf(responseRepository))
        )
        whenever(mockTrendingDAO.getTrendingRepositories()).doReturn(
            arrayListOf(responseRepository.toRepositoryEntity())
        )
        TrendingViewModel(getTrendingRepositories)
        runCurrent()

        verify(mockService, times(1)).getRepositories()
        verify(mockTrendingDAO, times(1)).deleteAll()
        verify(mockTrendingDAO, times(1)).insertAll(
            arrayListOf(responseRepository.toRepositoryEntity())
        )
        verify(mockTrendingDAO, times(2)).getTrendingRepositories()
    }

}