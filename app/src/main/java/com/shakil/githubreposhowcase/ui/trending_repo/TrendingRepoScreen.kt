package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shakil.githubreposhowcase.domain.DataStatus.*
import com.shakil.githubreposhowcase.domain.PagingKey
import com.shakil.githubreposhowcase.ui.common.shimmer.ShimmerList
import com.shakil.githubreposhowcase.ui.theme.Purple200

@Composable
fun TrendingRepoScreen(trendingRepoViewModel: TrendingRepoViewModel) {
    val state by trendingRepoViewModel.trendingRepoState.collectAsState()
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
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppBar { }
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

                when (state.status) {
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
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                color = Purple200,
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }

            }


        }

    }


}