package com.orangeink.trending.ui.trending

import android.app.Application
import androidx.lifecycle.*
import com.orangeink.trending.data.repo.TrendingRepo
import com.orangeink.trending.util.NetworkMonitor
import com.orangeink.trending.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class TrendingViewModel @Inject constructor(
    private val application: Application,
    private val repository: TrendingRepo
) : ViewModel() {

    val trendingRepositories = repository.getTrendingRepositories()

    val networkState = NetworkMonitor(application)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getRemoteRepositories() {
        viewModelScope.launch {
            when (val response = repository.remoteTrendingRepositories()) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> repository.updateDataToLocal(response.data)
            }
        }
    }

}