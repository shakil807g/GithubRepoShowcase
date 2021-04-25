package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakil.githubreposhowcase.domain.DataStatus
import com.shakil.githubreposhowcase.domain.PagingKey
import com.shakil.githubreposhowcase.domain.Resource
import com.shakil.githubreposhowcase.domain.Resource.Companion.idle
import com.shakil.githubreposhowcase.domain.model.TrendingRepo
import com.shakil.githubreposhowcase.interactors.TrendingRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingRepoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val trendingRepo: TrendingRepos
) : ViewModel() {

    private var requestInFlight = false
    var isRefreshing by mutableStateOf(false)
    var trendingRepoList by mutableStateOf(emptyList<TrendingRepo>())
    val trendingRepoState: MutableStateFlow<Resource<List<TrendingRepo>>> = MutableStateFlow(idle())

    fun fetchRepoList(pagingKey: PagingKey) {
        if (!requestInFlight) {
            requestInFlight = true
            if (pagingKey == PagingKey.REFRESH) isRefreshing = true
            viewModelScope.launch {
                trendingRepo.execute(pagingKey).collect {
                    trendingRepoState.value = it
                    if (it.status == DataStatus.SUCCESS) {
                        requestInFlight = false
                        isRefreshing = false
                        trendingRepoList = it.data ?: emptyList()
                    } else if (it.status == DataStatus.ERROR) {
                        requestInFlight = false
                        isRefreshing = false
                    } else if(it.status == DataStatus.IDLE) {
                        requestInFlight = false
                        isRefreshing = false
                    }
                }
            }
        }
    }

}