package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TrendingRepoScreen(trendingRepoViewModel: TrendingRepoViewModel) {
    val state by trendingRepoViewModel.trendingRepoState.collectAsState()

    when(state.data) {
        /*DataStatus.SUCCESS -> {

        }
*/
    }
}