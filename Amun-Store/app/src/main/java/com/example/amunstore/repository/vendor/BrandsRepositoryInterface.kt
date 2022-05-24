package com.example.amunstore.repository.vendor

import com.example.amunstore.model.Brands.BrandResponse
import com.example.example.SmartCollections
import retrofit2.Response

interface BrandsRepositoryInterface {

    suspend fun getBrands() :Response<BrandResponse>
}