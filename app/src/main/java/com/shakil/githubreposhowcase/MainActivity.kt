package com.shakil.githubreposhowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateOf
import com.shakil.githubreposhowcase.ui.theme.GithubRepoShowcaseTheme
import com.shakil.githubreposhowcase.ui.trending_repo.TrendingRepoScreen
import com.shakil.githubreposhowcase.ui.trending_repo.TrendingRepoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.shakil.githubreposhowcase.util.SystemUiController

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val trendingRepoViewModel: TrendingRepoViewModel by viewModels()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentTheme = isSystemInDarkTheme()
            val theme = remember { mutableStateOf(currentTheme) }
            val systemUiController = remember { SystemUiController(window) }
            GithubRepoShowcaseTheme(darkTheme = theme.value) {
                TrendingRepoScreen(theme.value, trendingRepoViewModel) { newTheme ->
                    theme.value = newTheme
                    systemUiController.setStatusBarColor(if (newTheme) Color.Black else Color.White)
                }
            }
        }
    }
}