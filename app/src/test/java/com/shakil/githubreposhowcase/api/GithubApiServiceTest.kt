package com.shakil.githubreposhowcase.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shakil.githubreposhowcase.MainCoroutinesRule
import com.shakil.githubreposhowcase.network.GithubApiService
import com.shakil.githubreposhowcase.util.Constants.PER_PAGE
import com.shakil.githubreposhowcase.util.Constants.START_PAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.CoreMatchers.`is`

@ExperimentalCoroutinesApi
class GithubApiServiceTest: ApiAbstract<GithubApiService>() {
    @get: Rule
    val coroutinesRule = MainCoroutinesRule()

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: GithubApiService

    @Before
    fun initService() {
        service = createService(GithubApiService::class.java)
    }

    @Test
    fun getTrendingRepoFromNetwork_return2Items() = runBlocking {
        enqueueResponse("GithubResponse.json")
        val response = (service.getTrendingRepos(START_PAGE, PER_PAGE))
        mockWebServer.takeRequest()
        assertThat(response.items?.count(), `is`(2))
    }

    @Test
    fun getTrendingRepoFromNetwork_returnOwnerUrl() = runBlocking {
        enqueueResponse("GithubResponse.json")
        val response = (service.getTrendingRepos(START_PAGE, PER_PAGE))
        mockWebServer.takeRequest()
        assertThat(response.items?.get(0)?.owner?.avatarUrl, `is`("https://avatars.githubusercontent.com/u/4314092?v=4"))
    }

    @Test
    fun getTrendingRepoFromNetwork_returnLanguage() = runBlocking {
        enqueueResponse("GithubResponse.json")
        val response = (service.getTrendingRepos(START_PAGE, PER_PAGE))
        mockWebServer.takeRequest()
        assertThat(response.items?.get(0)?.language, `is`("Go"))
    }

    @Test
    fun getTrendingRepoFromNetwork_returnErrorItems() = runBlocking {
        enqueueResponse("GithubResponseError.json")
        val response = (service.getTrendingRepos(START_PAGE, PER_PAGE))
        mockWebServer.takeRequest()
        assertThat(response.message, `is`("Only the first 1000 search results are available"))
    }

}