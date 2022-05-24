package com.example.amunstore.repository.vendor

import com.example.amunstore.model.Brands.BrandResponse
import com.example.amunstore.network.BrandsService
import com.example.amunstore.network.ProductsServices
import retrofit2.Response

class BrandsRepositoryImp(private val brandsService: BrandsService) : BrandsRepositoryInterface {


    override suspend fun getBrands(): Response<BrandResponse> =
        brandsService.getBrands()

}