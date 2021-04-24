package com.shakil.githubreposhowcase.network

import com.shakil.githubreposhowcase.network.model.TrendingRepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService  {
    @GET("search/repositories?q=language=+sort:stars")
    suspend fun getTrendingRepos( @Query("page") page: Int,
                                  @Query("per_page") per_page: Int): TrendingRepoResponse
}