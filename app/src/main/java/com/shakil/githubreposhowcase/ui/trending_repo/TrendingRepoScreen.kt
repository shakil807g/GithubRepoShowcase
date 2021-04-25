package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shakil.githubreposhowcase.domain.DataStatus.*
import com.shakil.githubreposhowcase.domain.PagingKey
import com.shakil.githubreposhowcase.ui.common.shimmer.ShimmerList
import com.shakil.githubreposhowcase.ui.theme.Purple200
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun TrendingRepoScreen(
    darkTheme: Boolean,
    trendingRepoViewModel: TrendingRepoViewModel,
    onThemeChange: (newTheme: Boolean) -> Unit
) {
    val list = trendingRepoViewModel.trendingRepoList
    val refreshState = rememberSwipeRefreshState(trendingRepoViewModel.isRefreshing)
    val scrollState = rememberLazyListState()

    DisposableEffect(Unit) {
        trendingRepoViewModel.fetchRepoList(PagingKey.INITIAL)
        onDispose { }
    }

    SwipeRefresh(
        state = refreshState,
        onRefresh = {
            trendingRepoViewModel.fetchRepoList(PagingKey.REFRESH)
        },indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onBackground
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)) {
            AppBar(darkTheme) { onThemeChange(it) }
            Box {
                if (!list.isNullOrEmpty()) {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(list) { index, item ->
                            if (list.lastIndex == index) {
                                SideEffect {
                                    trendingRepoViewModel.fetchRepoList(PagingKey.PAGE_END)
                                }
                            }
                            ListItem(item) {

                            }
                        }
                    }
                }

                when (trendingRepoViewModel.trendingRepoState.status) {
                    LOADING -> {
                        ShimmerList()
                    }
                    APPEND_LOADING -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                color = Purple200,
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                    ERROR -> {
                        ErrorItem(trendingRepoViewModel.trendingRepoState.error) {
                            trendingRepoViewModel.fetchRepoList(PagingKey.INITIAL)
                        }
                    }
                }

            }

        }

    }


}