package com.example.amunstore.data.network

import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.product.ProductImagesResponse
import com.example.amunstore.data.model.product.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductServices {

    @GET("products.json")
    suspend fun getProducts(
    ): Response<ProductsResponse>

    @GET("products.json")
    suspend fun getProductsByCategory(
        @Query("collection_id") collectionID: String
    ): Response<ProductsResponse>

    @GET("products/{id_product}.json")
    suspend fun getProductsByID(
        @Path("id_product") productID: Long
    ): Response<ProductDetailsResponse>

    @GET("products/{product_id}/images.json")
    suspend fun getProductImages(
        @Path("product_id") productID: Long
    ): Response<ProductImagesResponse>


}