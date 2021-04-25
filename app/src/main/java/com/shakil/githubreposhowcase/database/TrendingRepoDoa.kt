package com.shakil.githubreposhowcase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shakil.githubreposhowcase.database.model.TrendingRepoEntity

@Dao
interface TrendingRepoDoa {
    @Insert
    suspend fun insertTrendingRepo(trendingRepo: TrendingRepoEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrendingRepos(trendingRepos: List<TrendingRepoEntity>): LongArray

    @Query("DELETE FROM repo")
    suspend fun deleteAllTrendingRepo()

    @Query("SELECT COUNT(*) FROM repo")
    suspend fun getCount(): Int

    @Query("SELECT * FROM repo ORDER BY stars DESC")
    suspend fun getAllRepo(): List<TrendingRepoEntity>

}