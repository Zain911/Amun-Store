package com.example.amunstore.repository.productvendor

import com.example.amunstore.network.NetworkServices

class ProductVendorRepository ( private val networkServices: NetworkServices) :ProductVendorRepositoryInterface{


    override suspend fun getProductsVendor(vendorId: String) =
        networkServices.getProductsBrand(vendorId)

}