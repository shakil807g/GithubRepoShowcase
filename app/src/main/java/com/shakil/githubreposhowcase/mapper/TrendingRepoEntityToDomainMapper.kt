package com.shakil.githubreposhowcase.mapper

import com.shakil.githubreposhowcase.database.model.TrendingRepoEntity
import com.shakil.githubreposhowcase.domain.model.TrendingRepo
import com.shakil.githubreposhowcase.util.Mapper
import javax.inject.Inject

class TrendingRepoEntityToDomainMapper @Inject constructor() : Mapper<List<TrendingRepoEntity>, List<TrendingRepo>> {
    override suspend fun map(input: List<TrendingRepoEntity>): List<TrendingRepo> {
        return if (!input.isNullOrEmpty()) {
            input.map { item ->
                TrendingRepo(
                    id = item.id,
                    username = item.username,
                    libraryName = item.libraryName,
                    language = item.language,
                    description = item.description,
                    imageUrl = item.imageUrl,
                    stars = item.stars.toString()
                )
            }
        } else {
            emptyList()
        }
    }
}