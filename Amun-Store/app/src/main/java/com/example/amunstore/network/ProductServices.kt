package com.example.amunstore.network

import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.model.product.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductServices {

    @GET("products.json")
    suspend fun getProducts(
    ): Response<ProductsResponse>


    //https://mobile-ismailia.myshopify.com//admin/api/2022-04/products.json?collection_id=395728158949
    @GET("products.json")
    suspend fun getProductsByCategory(@Query("collection_id") collectionID : String
    ): Response<ProductsResponse>

    //https://c48655414af1ada2cd256a6b5ee391be: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@mobile-ismailia.myshopify.com/admin/api/2022-04/products.json?product=7782820643045
    //https://c48655414af1ada2cd256a6b5ee391be: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@mobile-ismailia.myshopify.com/admin/api/2022-04/products/7782820643045.json
    @GET("products/{id_product}.json")
    suspend fun getProductsByID(@Path("id_product")productID : Long
}