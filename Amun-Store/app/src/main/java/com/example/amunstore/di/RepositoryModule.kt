package com.example.amunstore.di

import com.example.amunstore.network.ProductsServices
import com.example.amunstore.repository.ProductDto
import com.example.amunstore.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        productServices: ProductsServices,
        productsDto: ProductDto
    ): ProductsRepository {
        return ProductsRepository(productServices, productsDto)
    }
}