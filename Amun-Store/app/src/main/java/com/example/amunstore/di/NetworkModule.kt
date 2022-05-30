package com.example.amunstore.di

import com.example.amunstore.network.AuthInterceptor
import com.example.amunstore.network.NetworkServices
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrl =
        "https://mobile-ismailia.myshopify.com/admin/api/2022-04/"

    @Singleton
    @Provides
    fun provideProductsService(): NetworkServices {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor()).build()
            )
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NetworkServices::class.java)
    }
}