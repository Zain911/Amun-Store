package com.example.amunstore.di

import android.content.Context
import androidx.room.Room
import com.example.amunstore.data.presistentstorage.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, AppDatabase::class.java, "amun_database").allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideProductsDao(db: AppDatabase) = db.productsDao()

    @Provides
    @Singleton
    fun provideItemCart(appDatabase: AppDatabase)=appDatabase.cartDao()
}