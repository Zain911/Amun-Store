package com.example.amunstore.repository.details

import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.model.product.ProductsResponse
import com.example.example.CategoriesResponse
import retrofit2.Response

interface ProductDetailsRepositoryInterface {

    suspend fun getProductsByID(byId:String ): Response<ProductDetailsResponse>

}