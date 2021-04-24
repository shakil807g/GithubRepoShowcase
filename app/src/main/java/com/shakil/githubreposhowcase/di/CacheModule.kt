package com.shakil.githubreposhowcase.di

import android.content.Context
import androidx.room.Room
import com.shakil.githubreposhowcase.database.AppDatabase
import com.shakil.githubreposhowcase.database.TrendingRepoDoa
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTrendingRepoDoa(db: AppDatabase): TrendingRepoDoa{
        return db.trendingRepoDao()
    }

}
