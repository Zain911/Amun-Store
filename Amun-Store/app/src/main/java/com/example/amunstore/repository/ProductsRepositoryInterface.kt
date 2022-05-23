package com.example.amunstore.repository

import com.example.amunstore.model.ProductsResponse
import retrofit2.Response

interface ProductsRepositoryInterface {

    fun getMenCategoryProducts()

    fun getWomanCategoryProducts()

    fun getKidsCategoryProducts()

    fun getSaleCategoryProducts()

    suspend fun getAllProducts() : Response<ProductsResponse>
}