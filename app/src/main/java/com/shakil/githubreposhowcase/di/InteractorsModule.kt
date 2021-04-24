package com.shakil.githubreposhowcase.di

import com.shakil.githubreposhowcase.database.TrendingRepoDoa
import com.shakil.githubreposhowcase.interactors.TrendingRepos
import com.shakil.githubreposhowcase.mapper.TrendingRepoEntityToDomainMapper
import com.shakil.githubreposhowcase.mapper.TrendingRepoResponseToEntityMapper
import com.shakil.githubreposhowcase.network.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Provides
    @Singleton
    fun provideTrendingRepos(
        trendingRepoDoa: TrendingRepoDoa,
        service: GithubApiService,
        trendingRepoResponseToEntityMapper: TrendingRepoResponseToEntityMapper,
        trendingRepoEntityToDomainMapper: TrendingRepoEntityToDomainMapper,
    ): TrendingRepos {
        return TrendingRepos(
            trendingRepoDoa,
            service,
            trendingRepoResponseToEntityMapper,
            trendingRepoEntityToDomainMapper
        )
    }

}