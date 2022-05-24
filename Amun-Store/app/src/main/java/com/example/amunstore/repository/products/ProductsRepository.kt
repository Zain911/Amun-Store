package com.example.amunstore.repository.products

import com.example.amunstore.model.product.ProductsResponse
import com.example.amunstore.network.NetworkServices
import com.example.example.CustomCollections
import retrofit2.Response

class ProductsRepository(
    private val networkServices: NetworkServices,
    private val productsDao: ProductDto
) : ProductsRepositoryInterface {


    override suspend fun getProductByCategory(category: CustomCollections): Response<ProductsResponse> =
        networkServices.getProductsByCategory(category.id.toString())


    override suspend fun getAllProducts() =
        networkServices.getProducts()

}