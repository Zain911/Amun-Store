package com.example.amunstore.repository.productvendor

import com.example.amunstore.model.productvendors.ProductVendorResponse
import retrofit2.Response

interface ProductVendorRepositoryInterface {


    suspend fun getProductsVendor(vendorId:String) :Response<ProductVendorResponse>
}