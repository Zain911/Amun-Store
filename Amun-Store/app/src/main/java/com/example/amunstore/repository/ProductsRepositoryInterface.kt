package com.example.amunstore.repository

interface ProductsRepositoryInterface {

    fun getMenCategoryProducts()

    fun getWomanCategoryProducts()

    fun getKidsCategoryProducts()

    fun getSaleCategoryProducts()

    suspend fun getAllProducts()
}