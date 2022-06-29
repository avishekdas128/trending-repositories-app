package com.orangeink.trending.feature_trending.presentation.util

import com.orangeink.trending.core.util.NetworkStatus
import com.orangeink.trending.feature_trending.presentation.TrendingRepositoryState

fun offlineModeWithCachedData(
    state: TrendingRepositoryState,
    offlineState: NetworkStatus?
): Boolean = !state.isLoading && !state.noNetwork && offlineState is NetworkStatus.Unavailable

fun loadingModeWithNoData(state: TrendingRepositoryState): Boolean =
    state.isLoading && state.trendingRepositories.isEmpty()

fun errorModeWithNoData(state: TrendingRepositoryState): Boolean =
    state.noNetwork && !state.isLoading

fun reloadDataMode(
    state: TrendingRepositoryState,
    offlineState: NetworkStatus?
): Boolean = !state.isLoading && state.noNetwork && offlineState is NetworkStatus.Available