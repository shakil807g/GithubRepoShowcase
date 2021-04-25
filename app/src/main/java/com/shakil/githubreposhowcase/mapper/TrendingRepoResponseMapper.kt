package com.shakil.githubreposhowcase.mapper

import com.shakil.githubreposhowcase.domain.model.TrendingRepo
import com.shakil.githubreposhowcase.network.model.TrendingRepoResponse
import com.shakil.githubreposhowcase.util.Mapper
import javax.inject.Inject

class TrendingRepoResponseMapper @Inject constructor() : Mapper<TrendingRepoResponse, List<TrendingRepo>> {
    override suspend fun map(input: TrendingRepoResponse): List<TrendingRepo> {
        return if (input.items != null && !input.items.isNullOrEmpty()) {
            input.items.mapNotNull { item ->
                TrendingRepo(
                    id = item?.id ?: -1,
                    username = item?.owner?.login ?: "",
                    libraryName = item?.name ?: "",
                    language = item?.language ?: "",
                    description = item?.description ?: "",
                    imageUrl = item?.owner?.avatarUrl ?: "",
                    stars = item?.stargazersCount.toString()
                )
            }
        } else {
            emptyList()
        }
    }
}