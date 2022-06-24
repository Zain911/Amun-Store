package com.example.amunstore.di

import com.example.amunstore.data.network.NetworkServices
import com.example.amunstore.data.repositories.categories.CategoriesRepository
import com.example.amunstore.data.presistentstorage.room.ProductsDao
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import com.example.amunstore.data.repositories.coupon.DiscountRepository
import com.example.amunstore.data.repositories.draftorder.DraftOrderRepository
import com.example.amunstore.data.repositories.orders.OrdersRepository
import com.example.amunstore.data.repositories.products.ProductsRepository
import com.example.amunstore.data.repositories.productvendor.ProductVendorRepository
import com.example.amunstore.data.repositories.user.UserRepository
import com.example.amunstore.data.repositories.vendor.BrandsRepository
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
        productsDao: ProductsDao,
    ): ProductsRepository {
        return ProductsRepository(productsDao, networkServices)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(networkServices: NetworkServices): CategoriesRepository {
        return CategoriesRepository(networkServices)
    }

    @Singleton
    @Provides
    fun provideVendorRepository(networkServices: NetworkServices): BrandsRepository {
        return BrandsRepository(networkServices)
    }

    @Singleton
    @Provides
    fun provideVendorProductsRepository(networkServices: NetworkServices): ProductVendorRepository {
        return ProductVendorRepository(networkServices)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        networkServices: NetworkServices,
        userSharedPreferences: UserSharedPreferences,
    ): UserRepository {
        return UserRepository(networkServices, userSharedPreferences)
    }

    @Singleton
    @Provides
    fun provideOrdersRepository(networkServices: NetworkServices): OrdersRepository {
        return OrdersRepository(networkServices)
    }

    @Singleton
    @Provides
    fun provideDiscountRepository(networkServices: NetworkServices): DiscountRepository {
        return DiscountRepository(networkServices)
    }

    @Singleton
    @Provides
    fun provideDraftOrderRepository(networkServices: NetworkServices) : DraftOrderRepository {
        return DraftOrderRepository(networkServices)
    }
}