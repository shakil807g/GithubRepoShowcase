package com.shakil.githubreposhowcase.interactors

import com.shakil.githubreposhowcase.database.TrendingRepoDoa
import com.shakil.githubreposhowcase.domain.PagingKey
import com.shakil.githubreposhowcase.domain.Resource
import com.shakil.githubreposhowcase.domain.model.TrendingRepo
import com.shakil.githubreposhowcase.mapper.TrendingRepoEntityToDomainMapper
import com.shakil.githubreposhowcase.mapper.TrendingRepoResponseToEntityMapper
import com.shakil.githubreposhowcase.network.GithubApiService
import com.shakil.githubreposhowcase.util.Constants.MAX_ITEMS
import com.shakil.githubreposhowcase.util.Constants.PER_PAGE
import com.shakil.githubreposhowcase.util.Constants.START_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TrendingRepos(
    private val trendingRepoDoa: TrendingRepoDoa,
    private val service: GithubApiService,
    private val trendingRepoResponseToEntityMapper: TrendingRepoResponseToEntityMapper,
    private val trendingRepoEntityToDomainMapper: TrendingRepoEntityToDomainMapper
) {

    fun execute(pagingKey: PagingKey): Flow<Resource<List<TrendingRepo>>> = flow {

        if (pagingKey == PagingKey.INITIAL) {
            emit(Resource.loading<List<TrendingRepo>>())
        } else if (pagingKey == PagingKey.PAGE_END) {
            emit(Resource.append_loading<List<TrendingRepo>>())
        }
        when (pagingKey) {
            PagingKey.INITIAL -> {
                val totalItemOnDb = trendingRepoDoa.getCount()
                if (totalItemOnDb == 0) {
                    emit(Resource.success(getFreshData()))
                } else {
                    val cacheRep = trendingRepoDoa.getAllRepo()
                    emit(Resource.success(trendingRepoEntityToDomainMapper.map(cacheRep)))
                }
            }
            PagingKey.REFRESH -> {
                emit(Resource.success(getFreshData()))
            }
            PagingKey.PAGE_END -> {
                val totalItemOnDb = trendingRepoDoa.getCount()
                if (totalItemOnDb == MAX_ITEMS) {
                    emit(Resource.idle())
                    return@flow
                }
                val nextPageToLoad = (totalItemOnDb / PER_PAGE) + 1
                val repoResponse = service.getTrendingRepos(nextPageToLoad, PER_PAGE)
                trendingRepoDoa.insertTrendingRepos(
                    trendingRepoResponseToEntityMapper.map(
                        repoResponse
                    )
                )
                emit(Resource.success(trendingRepoEntityToDomainMapper.map(trendingRepoDoa.getAllRepo())))
            }
        }
    }.catch { e ->
        val cacheRep = trendingRepoDoa.getAllRepo()
        if (cacheRep.isEmpty()) {
            emit(Resource.error(e))
        } else {
            emit(Resource.success(trendingRepoEntityToDomainMapper.map(cacheRep)))
        }
    }

    private suspend fun getFreshData(): List<TrendingRepo> {
        trendingRepoDoa.deleteAllTrendingRepo()
        val repoResponse = service.getTrendingRepos(START_PAGE, PER_PAGE)
        trendingRepoDoa.insertTrendingRepos(trendingRepoResponseToEntityMapper.map(repoResponse))
        return trendingRepoEntityToDomainMapper.map(trendingRepoDoa.getAllRepo())
    }


}