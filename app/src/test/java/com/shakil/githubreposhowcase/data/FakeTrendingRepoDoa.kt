package com.shakil.githubreposhowcase.data

import com.shakil.githubreposhowcase.database.TrendingRepoDoa
import com.shakil.githubreposhowcase.database.model.TrendingRepoEntity

class FakeTrendingRepoDoa(
    private val fakeAppDatabase: FakeAppDatabase
) : TrendingRepoDoa {

    override suspend fun insertTrendingRepo(trendingRepo: TrendingRepoEntity): Long {
        fakeAppDatabase.list.add(trendingRepo)
        return 1 // return success
    }

    override suspend fun insertTrendingRepos(trendingRepos: List<TrendingRepoEntity>): LongArray {
        fakeAppDatabase.list.addAll(trendingRepos)
        return longArrayOf(1) // return success
    }

    override suspend fun deleteAllTrendingRepo() {
        fakeAppDatabase.list.clear()
    }

    override suspend fun getCount(): Int {
        return fakeAppDatabase.list.count()
    }

    override suspend fun getAllRepo(): List<TrendingRepoEntity> {
        return fakeAppDatabase.list
    }
}