package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakil.githubreposhowcase.domain.DataStatus
import com.shakil.githubreposhowcase.domain.PagingKey
import com.shakil.githubreposhowcase.domain.Resource.Companion.idle
import com.shakil.githubreposhowcase.domain.model.TrendingRepo
import com.shakil.githubreposhowcase.interactors.TrendingRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class TrendingRepoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val trendingRepo: TrendingRepos
) : ViewModel() {

    private var requestInFlight = false
    var isRefreshing by mutableStateOf(false)
    var trendingRepoList by mutableStateOf(emptyList<TrendingRepo>())
    var trendingRepoState by mutableStateOf(idle<List<TrendingRepo>>())
    var lastPagingKey = PagingKey.INITIAL

    private val pagingRepoFlow: MutableSharedFlow<PagingKey> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)


    init {
        pagingRepoFlow
            .debounce(1000).flatMapLatest { pagingKey ->
                lastPagingKey = pagingKey
                trendingRepo.execute(pagingKey)
            }
            .map {
                trendingRepoState = it
                if (it.status == DataStatus.SUCCESS) {
                    requestInFlight = false
                    isRefreshing = false
                    trendingRepoList = it.data ?: emptyList()
                } else if (it.status == DataStatus.ERROR) {
                    requestInFlight = false
                    isRefreshing = false
                    if (lastPagingKey == PagingKey.REFRESH)
                        trendingRepoList = emptyList()
                } else if (it.status == DataStatus.IDLE) {
                    requestInFlight = false
                    isRefreshing = false
                }
            }
            .launchIn(viewModelScope)

    }

    fun fetchRepoList(pagingKey: PagingKey) {
        if (!requestInFlight) {
            requestInFlight = true
            if (pagingKey == PagingKey.REFRESH) isRefreshing = true
            viewModelScope.launch {
                pagingRepoFlow.emit(pagingKey)
            }
        }
    }

}