package com.example.amunstore.repository.products

import androidx.lifecycle.LiveData
import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.model.product.Product
import com.example.amunstore.model.product.ProductsResponse
import com.example.example.CustomCollections
import retrofit2.Response

interface ProductsRepositoryInterface {

    suspend fun getProductByCategory(category: CustomCollections): Response<ProductsResponse>

    suspend fun getAllProducts(): Response<ProductsResponse>

    //for details about product
    suspend fun getProductsByID(byId: Long): Response<ProductDetailsResponse>

    suspend fun getAllFavouriteProducts() : LiveData<List<Product>>

    fun addProductToFavourite(product: Product)

    fun removeProductFromFavourite(product: Product)
    
}