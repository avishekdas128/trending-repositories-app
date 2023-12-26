package com.orangeink.trending.feature_trending.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orangeink.trending.R
import com.orangeink.trending.core.util.NetworkMonitor
import com.orangeink.trending.feature_trending.presentation.components.NoNetwork
import com.orangeink.trending.feature_trending.presentation.components.OfflineInfo
import com.orangeink.trending.feature_trending.presentation.components.RepositoryItem
import com.orangeink.trending.feature_trending.presentation.components.SearchAppBar
import com.orangeink.trending.feature_trending.presentation.util.errorModeWithNoData
import com.orangeink.trending.feature_trending.presentation.util.loadingModeWithNoData
import com.orangeink.trending.feature_trending.presentation.util.offlineModeWithCachedData
import com.orangeink.trending.ui.theme.TrendingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class TrendingActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendingTheme {
                val viewModel: TrendingViewModel = hiltViewModel()
                val state = viewModel.state.value
                val offlineState = networkMonitor.observeAsState().value
                val scaffoldState = rememberScaffoldState()
                val focusRequester = remember { FocusRequester() }
                var show by remember {
                    mutableStateOf(false)
                }

                LaunchedEffect(true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is TrendingViewModel.UIEvent.ShowSnackBar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.trending),
                                    fontSize = 20.sp
                                )
                            },
                            backgroundColor = MaterialTheme.colors.surface,
                            actions = {
                                IconButton(onClick = {
                                    if (state.trendingRepositories.isNotEmpty())
                                        show = !show
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_search),
                                        contentDescription = stringResource(id = R.string.search),
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                }
                            }
                        )
                        Crossfade(targetState = show, label = "searchAnimation") {
                            if (it) {
                                SearchAppBar(
                                    text = viewModel.search.value,
                                    onTextChange = viewModel::onSearch,
                                    onCloseClicked = {
                                        viewModel.onSearch("")
                                        show = !show
                                    },
                                    onSearchClicked = viewModel::onSearch,
                                    focusRequester = focusRequester
                                )
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .padding(it)
                    ) {
                        Column {
                            if (offlineModeWithCachedData(state, offlineState)) {
                                OfflineInfo()
                            }
                            TrendingList(state = state)
                        }
                        if (loadingModeWithNoData(state)) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                        if (errorModeWithNoData(state)) {
                            NoNetwork(Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingList(
    state: TrendingRepositoryState,
    modifier: Modifier = Modifier
) {
    val lazyColumnState = rememberLazyListState()
    LazyColumn(
        state = lazyColumnState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        items(
            count = state.trendingRepositories.size,
            key = { index ->
                state.trendingRepositories[index].id
            }
        ) { i ->
            val repository = state.trendingRepositories[i]
            RepositoryItem(repository = repository)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

