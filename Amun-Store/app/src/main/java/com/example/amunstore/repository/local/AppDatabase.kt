package com.example.amunstore.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.amunstore.model.product.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}