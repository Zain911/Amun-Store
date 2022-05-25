package com.example.amunstore.repository.vendor

import com.example.amunstore.model.Brands.BrandResponse
import com.example.amunstore.network.BrandsService
import retrofit2.Response
import javax.inject.Inject

class BrandsRepository (private val brandsService: BrandsService) : BrandsRepositoryInterface {

    override suspend fun getBrands(): Response<BrandResponse> =
        brandsService.getBrands()

}