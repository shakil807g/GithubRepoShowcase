package com.shakil.githubreposhowcase.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.shakil.githubreposhowcase.ui.theme.Purple200

data class TrendingRepo(
    val id: Int,
    val username: String,
    val libraryName: String,
    val language: String,
    val description: String,
    val imageUrl: String,
    val stars: String,
    val color: Color = Purple200,
    val isExpanded: MutableState<Boolean> = mutableStateOf(false)
)