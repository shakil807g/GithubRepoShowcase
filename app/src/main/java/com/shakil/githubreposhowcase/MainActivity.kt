package com.shakil.githubreposhowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import com.shakil.githubreposhowcase.ui.theme.GithubRepoShowcaseTheme
import com.shakil.githubreposhowcase.ui.trending_repo.TrendingRepoScreen
import com.shakil.githubreposhowcase.ui.trending_repo.TrendingRepoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val trendingRepoViewModel: TrendingRepoViewModel by viewModels()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubRepoShowcaseTheme {
                TrendingRepoScreen(trendingRepoViewModel)
            }
        }
    }
}