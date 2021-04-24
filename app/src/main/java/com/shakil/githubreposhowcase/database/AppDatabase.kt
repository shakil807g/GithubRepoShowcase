package com.shakil.githubreposhowcase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shakil.githubreposhowcase.database.model.TrendingRepoEntity

@Database(entities = [TrendingRepoEntity::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): TrendingRepoDoa

    companion object{
        val DATABASE_NAME: String = "github_db"
    }


}