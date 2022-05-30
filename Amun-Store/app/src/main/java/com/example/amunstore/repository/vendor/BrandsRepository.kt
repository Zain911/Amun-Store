package com.example.amunstore.repository.vendor

import com.example.amunstore.model.brands.BrandResponse
import com.example.amunstore.network.BrandsService
import retrofit2.Response

class BrandsRepository (private val brandsService: BrandsService) : BrandsRepositoryInterface {

    override suspend fun getBrands(): Response<BrandResponse> =
        brandsService.getBrands()

}