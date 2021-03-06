package com.example.amunstore.data.presistentstorage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.product.Image
import com.example.amunstore.data.model.product.Product

@Database(entities = [Product::class , ItemCart::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
    abstract fun cartDao(): CartDao
}