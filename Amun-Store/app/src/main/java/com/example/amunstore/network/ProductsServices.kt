package com.example.amunstore.network

import com.example.amunstore.model.ProductDetailsResponse
import com.example.amunstore.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsServices {

    @GET("products.json")
    suspend fun getProducts(
    ): Response<ProductsResponse>

    //retrieve prdouct details using it's ID
    @GET("/products/{productID}.json")
    suspend fun getProductDetails(@Path("productID") ID : String): Response<ProductDetailsResponse>

}