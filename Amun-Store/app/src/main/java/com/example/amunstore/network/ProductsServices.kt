package com.example.amunstore.network

import com.example.amunstore.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductsServices {

    @GET("products.json")
    suspend fun getProducts(
    ): Response<ProductsResponse>

}