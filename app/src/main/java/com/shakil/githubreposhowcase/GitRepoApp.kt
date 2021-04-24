package com.shakil.githubreposhowcase

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class GitRepoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader {
            val coilOkHttpClient = OkHttpClient.Builder()
                .cache(CoilUtils.createDefaultCache(this@GitRepoApp))
                .build()

            ImageLoader.Builder(this@GitRepoApp)
                .okHttpClient(coilOkHttpClient)
                .build()
        }
    }
}