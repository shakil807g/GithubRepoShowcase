package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakil.githubreposhowcase.domain.PagingKey
import com.shakil.githubreposhowcase.domain.Resource
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

    val trendingRepoState: MutableStateFlow<Resource<List<TrendingRepo>>> =
        MutableStateFlow(Resource.idle())

    fun fetchRepoList(pagingKey: PagingKey) {
        viewModelScope.launch {
            trendingRepo.execute(pagingKey).collect {
                trendingRepoState.value = it
            }
        }
    }

}