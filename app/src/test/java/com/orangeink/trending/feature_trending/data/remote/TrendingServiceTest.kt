package com.orangeink.trending.feature_trending.data.remote

import com.google.gson.stream.MalformedJsonException
import com.orangeink.trending.util.errorResponse
import com.orangeink.trending.util.responseRepository
import com.orangeink.trending.util.successfulResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class TrendingServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: TrendingService

    private val client = OkHttpClient.Builder().build()

    @Before
    fun createServer() {
        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // setting a dummy url
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrendingService::class.java)
    }

    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `correct response is parsed into success result`() = runTest {
        val response = MockResponse()
            .setBody(successfulResponse)
            .setResponseCode(200)
        mockWebServer.enqueue(response)
        val expectedActivity = arrayListOf(responseRepository)

        val result = service.getRepositories()

        assert(result.isSuccessful)
        assertEquals(result.body(), expectedActivity)
    }

    @Test
    fun `malformed response returns json error result`() = runTest {
        val response = MockResponse()
            .setBody(errorResponse)
            .setResponseCode(200)
        mockWebServer.enqueue(response)

        var exception: Exception? = null

        try {
            service.getRepositories()
        } catch (e: Exception) {
            exception = e
        }

        assert(exception is MalformedJsonException)
    }

    @Test
    fun `error response returns http error result`() = runTest {
        val response = MockResponse()
            .setBody(successfulResponse)
            .setResponseCode(400)
        mockWebServer.enqueue(response)

        val result = service.getRepositories()

        assert(!result.isSuccessful)
        assertEquals(result.code(), 400)
    }

}