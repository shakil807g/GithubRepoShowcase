package com.shakil.githubreposhowcase.interactors

import com.shakil.githubreposhowcase.api.ApiAbstract
import com.shakil.githubreposhowcase.data.FakeAppDatabase
import com.shakil.githubreposhowcase.data.FakeTrendingRepoDoa
import com.shakil.githubreposhowcase.database.model.TrendingRepoEntity
import com.shakil.githubreposhowcase.domain.DataStatus
import com.shakil.githubreposhowcase.domain.PagingKey
import com.shakil.githubreposhowcase.mapper.TrendingRepoEntityToDomainMapper
import com.shakil.githubreposhowcase.mapper.TrendingRepoResponseToEntityMapper
import com.shakil.githubreposhowcase.network.GithubApiService
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class TrendingReposTest : ApiAbstract<GithubApiService>() {

    private val appDatabase = FakeAppDatabase()
    private val testList = listOf(
        TrendingRepoEntity(
            22, "Test user 1", "ABC", "GO", "", "", 222
        ), TrendingRepoEntity(
            44, "Test user 2", "ABC", "GO", "", "", 2
        ),TrendingRepoEntity(
            4444, "Test user 3", "ABC", "GO", "", "", 2
        )
    )

    // System in test
    private lateinit var trendingRepos: TrendingRepos

    // Dependencies
    private lateinit var service: GithubApiService
    private lateinit var trendingDoa: FakeTrendingRepoDoa
    private val trendingRepoResponseToEntityMapper = TrendingRepoResponseToEntityMapper()
    private val trendingRepoEntityToDomainMapper = TrendingRepoEntityToDomainMapper()


    @Before
    override fun setup() {
        super.setup()

        service = createService(GithubApiService::class.java)
        trendingDoa = FakeTrendingRepoDoa(appDatabase)

        // instantiate the system in test
        trendingRepos = TrendingRepos(
            trendingDoa,
            service,
            trendingRepoResponseToEntityMapper,
            trendingRepoEntityToDomainMapper
        )
    }

    @Test
    fun getTrendingRepoFromNetwork_emitTwoRepoFromCache(): Unit = runBlocking {
        enqueueResponse("GithubResponse.json")

        trendingRepos.execute(PagingKey.INITIAL).toList()

        assertThat(trendingDoa.getAllRepo().count(), `is`(2))
    }

    @Test
    fun getTrendingRepoFromNetwork_emit1ItemLoading(): Unit = runBlocking {
        enqueueResponse("GithubResponse.json")

        val flowItems = trendingRepos.execute(PagingKey.INITIAL).toList()

        assertThat(flowItems[0].status, `is`(equalTo(DataStatus.LOADING)))
    }

    @Test
    fun getTrendingRepoFromNetwork_emit2ItemSuccess(): Unit = runBlocking {
        enqueueResponse("GithubResponse.json")

        val flowItems = trendingRepos.execute(PagingKey.INITIAL).toList()

        assertThat(flowItems[1].status, `is`(equalTo(DataStatus.SUCCESS)))
    }

    @Test
    fun getTrendingRepoFromNetwork_emitSecondItemWith2Repo(): Unit = runBlocking {
        enqueueResponse("GithubResponse.json")

        val flowItems = trendingRepos.execute(PagingKey.INITIAL).toList()

        assertThat(flowItems[1].data?.count(), `is`(2))
    }

    @Test
    fun getTrendingRepoFromNetwork_emitSecondItemWithError(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = trendingRepos.execute(PagingKey.INITIAL).toList()

        assertThat(flowItems[1].status, `is`(equalTo(DataStatus.ERROR)))
    }

    @Test
    fun getTrendingRepoFromNetwork_shouldNotReturnError(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        trendingDoa.insertTrendingRepos(testList)
        val flowItems = trendingRepos.execute(PagingKey.INITIAL).toList()

        assertThat(flowItems[1].status, `is`(equalTo(DataStatus.SUCCESS)))
    }

}