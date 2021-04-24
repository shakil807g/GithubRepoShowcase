package com.shakil.githubreposhowcase.network

import com.shakil.githubreposhowcase.network.model.TrendingRepoResponse
import retrofit2.http.GET

interface GithubApiService  {
    @GET("search/repositories?q=language=+sort:stars")
    suspend fun getTrendingRepos(): TrendingRepoResponse?
}