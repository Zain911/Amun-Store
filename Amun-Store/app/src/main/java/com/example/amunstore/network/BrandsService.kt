package com.example.amunstore.network

import com.example.amunstore.model.Brands.BrandResponse
import retrofit2.Response
import retrofit2.http.GET

interface BrandsService {
    @GET("smart_collections.json")
    suspend fun getBrands(
    ): Response<BrandResponse>

}