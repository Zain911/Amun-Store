package com.example.amunstore.data.network

import com.example.amunstore.data.model.productvendors.ProductVendorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsVendorService {
    @GET("collections/{id_vendor}/products.json")
    suspend fun getProductsBrand(@Path("id_vendor") VendorID : String
    ): Response<ProductVendorResponse>

}