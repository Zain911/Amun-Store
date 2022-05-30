package com.example.amunstore.data.repositories.productvendor

import com.example.amunstore.data.model.productvendors.ProductVendorResponse
import retrofit2.Response

interface ProductVendorRepositoryInterface {


    suspend fun getProductsVendor(vendorId:String) :Response<ProductVendorResponse>
}