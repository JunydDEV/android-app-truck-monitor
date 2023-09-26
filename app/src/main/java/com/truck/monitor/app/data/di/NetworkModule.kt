package com.truck.monitor.app.data.di

import android.content.Context
import android.net.ConnectivityManager
import com.truck.monitor.app.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val retrofit: Retrofit = getRetrofit()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @get:Singleton
    @get:Provides
    val okHttpClient: OkHttpClient
        get() {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            return okHttpClientBuilder.build()
        }

    companion object {
        private const val BASE_URL = "https://interviewtestapi.azurewebsites.net/"
        private const val CONNECT_TIMEOUT = 30L
        private const val READ_TIMEOUT = 30L
        private const val WRITE_TIMEOUT = 30L
    }
}