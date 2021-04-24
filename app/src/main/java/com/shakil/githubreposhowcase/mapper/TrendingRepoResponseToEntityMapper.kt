package com.shakil.githubreposhowcase.mapper

import com.shakil.githubreposhowcase.database.model.TrendingRepoEntity
import com.shakil.githubreposhowcase.network.model.TrendingRepoResponse
import com.shakil.githubreposhowcase.util.Mapper
import javax.inject.Inject

class TrendingRepoResponseToEntityMapper @Inject constructor() : Mapper<TrendingRepoResponse, List<TrendingRepoEntity>> {
    override suspend fun map(input: TrendingRepoResponse): List<TrendingRepoEntity> {
        return if (input.items != null && !input.items.isNullOrEmpty()) {
            input.items.mapNotNull { item ->
                TrendingRepoEntity(
                    id = item?.id ?: -1,
                    username = item?.owner?.login ?: "",
                    libraryName = item?.name ?: "",
                    language = item?.language ?: "",
                    description = item?.description ?: "",
                    imageUrl = item?.owner?.avatarUrl ?: "",
                    stars = item?.stargazersCount ?: 0
                )
            }
        } else {
            emptyList()
        }
    }
}