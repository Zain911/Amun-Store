package com.example.amunstore.data.repositories.vendor

import com.example.amunstore.data.model.brands.BrandResponse
import retrofit2.Response

interface BrandsRepositoryInterface {

    suspend fun getBrands() :Response<BrandResponse>
}