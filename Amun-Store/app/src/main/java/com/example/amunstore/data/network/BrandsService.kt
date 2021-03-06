package com.example.amunstore.data.network

import com.example.amunstore.data.model.brands.BrandResponse
import retrofit2.Response
import retrofit2.http.GET

interface BrandsService {
    @GET("smart_collections.json")
    suspend fun getBrands(
    ): Response<BrandResponse>

}