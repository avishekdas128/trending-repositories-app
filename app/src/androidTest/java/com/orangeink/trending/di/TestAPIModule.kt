package com.orangeink.trending.di

import com.orangeink.trending.feature_trending.data.remote.TrendingService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [APIModule::class]
)
object TestAPIModule {

    @Provides
    @Singleton
    fun provideMockServer(): MockWebServer {
        return MockWebServer()
    }

    /**
     * We need to jump through the hoops a bit
     * to avoid NetworkOnMainThread exception
     * in our UI tests.
     */
    @Provides
    @Singleton
    fun provideBaseUrl(mockWebServer: MockWebServer): String {
        var url = ""
        val thread = Thread {
            url = mockWebServer.url("/").toString()
        }
        thread.start()
        thread.join()
        return url
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS) // Connect timeout
            .readTimeout(15, TimeUnit.SECONDS) // Read timeout
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRepositoryService(retrofit: Retrofit): TrendingService {
        return retrofit.create(TrendingService::class.java)
    }
}