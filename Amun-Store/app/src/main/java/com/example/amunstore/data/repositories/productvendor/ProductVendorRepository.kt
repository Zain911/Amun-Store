package com.example.amunstore.data.repositories.productvendor

import com.example.amunstore.data.network.NetworkServices

class ProductVendorRepository(private val networkServices: NetworkServices) :
    ProductVendorRepositoryInterface {
    
    override suspend fun getProductsVendor(vendorId: String) =
        networkServices.getProductsBrand(vendorId)

}