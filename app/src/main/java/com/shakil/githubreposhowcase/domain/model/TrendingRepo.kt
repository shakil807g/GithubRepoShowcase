package com.shakil.githubreposhowcase.domain.model

data class TrendingRepo(
    val id: Int,
    val username: String,
    val libraryName: String,
    val language: String,
    val description: String,
    val imageUrl: String,
    val stars: Int
)