package com.orangeink.trending.feature_trending.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.trending.core.util.Resource
import com.orangeink.trending.feature_trending.domain.model.Repository
import com.orangeink.trending.feature_trending.domain.repository.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: TrendingRepository
) : ViewModel() {

    private val _search = mutableStateOf("")
    val search: State<String> = _search

    private val _state = mutableStateOf(TrendingRepositoryState())
    val state: State<TrendingRepositoryState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var data: List<Repository> = emptyList()

    init {
        loadData()
    }

    fun loadData() {
        _state.value = TrendingRepositoryState()
        viewModelScope.launch {
            repository.getTrendingRepositories().onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        data = result.data ?: emptyList()
                        _state.value = _state.value.copy(
                            trendingRepositories = data,
                            isLoading = false,
                        )
                        if (data.isNotEmpty())
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    message = result.message ?: "Unknown Error!"
                                )
                            )
                    }
                    is Resource.Loading -> {
                        data = result.data ?: emptyList()
                        _state.value = _state.value.copy(
                            trendingRepositories = data,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        data = result.data ?: emptyList()
                        _state.value = _state.value.copy(
                            trendingRepositories = data,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun onSearch(query: String) {
        _search.value = query
        _state.value = _state.value.copy(
            trendingRepositories = data.filter {
                it.name.contains(query, true)
            },
            isLoading = false
        )
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}