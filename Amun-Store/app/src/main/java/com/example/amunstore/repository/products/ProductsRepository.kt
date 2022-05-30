package com.example.amunstore.repository.products

import androidx.lifecycle.LiveData
import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.model.product.Product
import com.example.amunstore.model.product.ProductsResponse
import com.example.amunstore.network.NetworkServices
import com.example.amunstore.repository.local.ProductsDao
import com.example.example.CustomCollections
import retrofit2.Response
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsDao: ProductsDao,
    private val networkServices: NetworkServices
) : ProductsRepositoryInterface {

    override suspend fun getProductByCategory(category: CustomCollections): Response<ProductsResponse> =
        networkServices.getProductsByCategory(category.id.toString())

    override suspend fun getAllProducts() =
        networkServices.getProducts()

    override suspend fun getProductsByID(byId: Long): Response<ProductDetailsResponse> =
        networkServices.getProductsByID(byId)






    override suspend fun getAllFavouriteProducts() = productsDao.getAllFavourite()

    override fun addProductToFavourite(product: Product) {
        productsDao.addItemToFavourite(product)
    }

    override fun removeProductFromFavourite(product: Product) {
        productsDao.addItemToFavourite(product)
    }


}