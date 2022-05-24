package com.example.amunstore.repository.details

import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.model.product.ProductsResponse
import com.example.amunstore.network.NetworkServices
import retrofit2.Response

class ProductDetailsRepository( private val networkServices: NetworkServices ) : ProductDetailsRepositoryInterface {


    override suspend fun getProductsByID(byId: String): Response<ProductDetailsResponse> {
     return networkServices.getProductsByID(byId)
    }


}