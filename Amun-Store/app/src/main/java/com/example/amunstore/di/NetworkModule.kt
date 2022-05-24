package com.example.amunstore.di

import android.annotation.SuppressLint
import com.example.amunstore.network.AuthInterceptor
import com.example.amunstore.network.NetworkServices
import com.example.amunstore.repository.products.ProductDto
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

    // api key --->  c48655414af1ada2cd256a6b5ee391be
    // password -->  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
    @SuppressLint("AuthLeak")
    val baseUrl =
        "https://mobile-ismailia.myshopify.com/admin/api/2022-04/"

    @Singleton
    @Provides
    fun provideProductsMapper(): ProductDto {
        return ProductDto()
    }

    @Singleton
    @Provides
    fun provideProductsService(): NetworkServices {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor()).build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NetworkServices::class.java)
    }
}