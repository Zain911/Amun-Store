package com.example.amunstore.network

import com.example.amunstore.model.product.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductServices {

    @GET("products.json")
    suspend fun getProducts(
    ): Response<ProductsResponse>


    //https://mobile-ismailia.myshopify.com//admin/api/2022-04/products.json?collection_id=395728158949
    @GET("products.json")
    suspend fun getProductsByCategory(@Query("collection_id") collectionID : String
    ): Response<ProductsResponse>

}