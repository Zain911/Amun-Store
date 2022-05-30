package com.example.amunstore.repository.vendor

import com.example.amunstore.model.brands.BrandResponse
import retrofit2.Response

interface BrandsRepositoryInterface {

    suspend fun getBrands() :Response<BrandResponse>
}