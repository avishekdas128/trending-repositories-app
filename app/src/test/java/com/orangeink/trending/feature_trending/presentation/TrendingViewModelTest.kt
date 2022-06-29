package com.orangeink.trending.feature_trending.presentation

import app.cash.turbine.test
import com.orangeink.trending.fake.use_case.FakeTrendingRepositories
import com.orangeink.trending.util.CoroutineRule
import com.orangeink.trending.util.repository1
import com.orangeink.trending.util.repository2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TrendingViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @Test
    fun `creating a viewModel exposes loading ui state`() {
        val viewModel = TrendingViewModel(
            FakeTrendingRepositories()
        )

        assert(viewModel.state.value.isLoading)
    }

    @Test
    fun `creating a viewModel updates UI state to success after loading`() {
        val viewModel = TrendingViewModel(
            FakeTrendingRepositories()
        )

        val expectedUIState = TrendingRepositoryState(
            trendingRepositories = arrayListOf(repository1, repository2),
            isLoading = false,
            noNetwork = false
        )
        coroutineRule.testDispatcher.scheduler.runCurrent()

        val actualState = viewModel.state.value
        assertEquals(actualState, expectedUIState)
    }

    @Test
    fun `creating a viewModel updates UI state to error in case of failure but have cached data`() =
        runTest {
            val viewModel = TrendingViewModel(
                FakeTrendingRepositories(isSuccessful = false)
            )

            val expectedUIState = TrendingRepositoryState(
                trendingRepositories = arrayListOf(repository1, repository2),
                isLoading = false,
                noNetwork = false
            )

            launch {
                viewModel.eventFlow.test {
                    with(awaitItem()) {
                        assert(this is TrendingViewModel.UIEvent.ShowSnackBar)
                    }

                    assertEquals(viewModel.state.value, expectedUIState)
                    cancelAndIgnoreRemainingEvents()
                }
            }
            runCurrent()
        }

    @Test
    fun `creating a viewModel updates UI state to no network error in case of failure and no cache`() {
        val viewModel = TrendingViewModel(
            FakeTrendingRepositories(
                isSuccessful = false,
                emptyCache = true
            )
        )

        val expectedUIState = TrendingRepositoryState(
            trendingRepositories = emptyList(),
            isLoading = false,
            noNetwork = true
        )
        coroutineRule.testDispatcher.scheduler.runCurrent()

        val actualState = viewModel.state.value
        assertEquals(actualState, expectedUIState)
    }

    @Test
    fun `calling loadData() updates UI state with new updated data`() {
        val fakeTrendingRepositories = FakeTrendingRepositories()
        val viewModel = TrendingViewModel(fakeTrendingRepositories)

        coroutineRule.testDispatcher.scheduler.runCurrent()

        val expectedUIState = TrendingRepositoryState(
            trendingRepositories = arrayListOf(repository2),
            isLoading = false,
            noNetwork = false
        )
        fakeTrendingRepositories.repository = arrayListOf(repository2)

        viewModel.loadData()
        coroutineRule.testDispatcher.scheduler.runCurrent()

        val actualState = viewModel.state.value
        assertEquals(actualState, expectedUIState)
    }

    @Test
    fun `calling onSearch() with query yolo updates UI state with new filtered data`() {
        val viewModel = TrendingViewModel(FakeTrendingRepositories())

        val expectedUIState = TrendingRepositoryState(
            trendingRepositories = arrayListOf(repository1),
            isLoading = false,
            noNetwork = false
        )

        coroutineRule.testDispatcher.scheduler.runCurrent()

        viewModel.onSearch("yolo")
        coroutineRule.testDispatcher.scheduler.runCurrent()

        val actualState = viewModel.state.value
        assertEquals(actualState, expectedUIState)
    }

    @Test
    fun `calling onSearch() with query gh updates UI state with no data`() {
        val viewModel = TrendingViewModel(FakeTrendingRepositories())

        val expectedUIState = TrendingRepositoryState(
            trendingRepositories = emptyList(),
            isLoading = false,
            noNetwork = false
        )

        coroutineRule.testDispatcher.scheduler.runCurrent()

        viewModel.onSearch("gh")
        coroutineRule.testDispatcher.scheduler.runCurrent()

        val actualState = viewModel.state.value
        assertEquals(actualState, expectedUIState)
    }
}