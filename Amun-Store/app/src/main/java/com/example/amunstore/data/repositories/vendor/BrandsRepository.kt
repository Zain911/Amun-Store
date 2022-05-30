package com.example.amunstore.data.repositories.vendor

import com.example.amunstore.data.model.brands.BrandResponse
import com.example.amunstore.data.network.BrandsService
import retrofit2.Response

class BrandsRepository (private val brandsService: BrandsService) : BrandsRepositoryInterface {

    override suspend fun getBrands(): Response<BrandResponse> =
        brandsService.getBrands()

}