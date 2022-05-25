package com.example.amunstore.di

import com.example.amunstore.network.NetworkServices
import com.example.amunstore.repository.categories.CategoriesRepository
import com.example.amunstore.repository.products.ProductDto
import com.example.amunstore.repository.products.ProductsRepository
import com.example.amunstore.repository.productvendor.ProductVendorRepository
import com.example.amunstore.repository.vendor.BrandsRepository
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
        networkServices: NetworkServices,
        productsDto: ProductDto
    ): ProductsRepository {
        return ProductsRepository(networkServices, productsDto)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(networkServices: NetworkServices): CategoriesRepository {
        return CategoriesRepository(networkServices)
    }

    @Singleton
    @Provides
    fun provideVendorRepository(networkServices: NetworkServices):BrandsRepository{
        return BrandsRepository(networkServices)
    }

    @Singleton
    @Provides
    fun provideVendorProductsRepository(networkServices: NetworkServices):ProductVendorRepository{
        return ProductVendorRepository(networkServices)
    }

}